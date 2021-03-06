package net.teamof.whisper.data

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.models.MessageSide
import net.teamof.whisper.models.UserAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConversationRepository @Inject constructor(
	private val conversationDAO: ConversationDAO,
	private val usersAPI: UsersAPI
) {

	val getAlLConversations: LiveData<List<Conversation>> =
		conversationDAO.getAllConversations()

	private fun isConversationExist(targetUser: Long): Long =
		conversationDAO.isConversationExist(targetUser)

	fun upsert(conversation: Conversation) =
		conversationDAO.upsert(conversation)

	fun upsert(conversations: MutableList<Conversation>) =
		conversationDAO.upsert(conversations)

	fun getByUsername(username: String): Conversation =
		conversationDAO.getByUsername(username)


	fun update(conversation: Conversation) =
		conversationDAO.update(conversation)


	fun update(conversation: MutableList<Conversation>) =
		conversationDAO.update(conversation)

	fun updateConversationByReceivingMessage(side: MessageSide, newMessage: Message) {
		if (isConversationExist(
				when (side) {
					MessageSide.THEMSELVES -> newMessage.sender
					MessageSide.MYSELF -> newMessage.receiver
				}
			) == 0L
		) {
			// we can also query from our room db before making any request!
			val response =
				usersAPI.getUserProfile(if (side == MessageSide.THEMSELVES) newMessage.sender.toString() else newMessage.receiver.toString())

			response.enqueue(object : Callback<UserAPI> {
				override fun onResponse(
					call: Call<UserAPI>,
					response: Response<UserAPI>
				) {
					CoroutineScope(Dispatchers.IO).launch {
						response.body()?.let {
							upsert(
								Conversation(
									target_user = if (side == MessageSide.THEMSELVES) newMessage.sender else newMessage.receiver,
									last_message = newMessage.content,
									last_message_time = newMessage.createdAt ?: Date(),
									unread_messages = 0,
									username = it.username,
									avatar = it.avatar ?: ""
								)
							)
						}
					}
				}

				override fun onFailure(call: Call<UserAPI>, t: Throwable) {
					Log.e("On Failure usersAPI.getUserProfile", t.toString())
				}

			})
		} else {
			val getConversation =
				conversationDAO.getConversationByUseId(if (side == MessageSide.THEMSELVES) newMessage.sender else newMessage.receiver)

			getConversation.last_message = newMessage.content
			getConversation.last_message_time = newMessage.createdAt ?: Date()

			update(getConversation)
		}
	}

	fun delete(conversation: Conversation) =
		conversationDAO.delete(conversation)

	fun deleteByUserIds(ids: MutableList<Long>) {
		conversationDAO.deleteByUserIds(ids)
		// i should also inject messagesDAO and delete user messages
	}

	fun getAll(): List<Conversation> =
		conversationDAO.getAll()
}