package net.teamof.whisper.repositories

import io.objectbox.Box
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.oneOf
import io.objectbox.kotlin.or
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ConversationRepository @Inject constructor(
	private val usersAPI: UsersAPI
) {
	private val conversationBox: Box<Conversation> =
		ObjectBox.store.boxFor(Conversation::class.java)

	private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

	private fun isConversationExist(to_user_id: Long): Long {
		val query = conversationBox.query().equal(Conversation_.to_user_id, to_user_id).build()
		val result = query.count()
		query.close()

		return result
	}

	fun update(side: MessageSide, newMessage: Message) {
		if (isConversationExist(
				when (side) {
					MessageSide.THEMSELVES -> newMessage.user_id
					MessageSide.MYSELF -> newMessage.to_user_id
				}
			) == 0L
		) {
			val response =
				usersAPI.getUserProfile(if (side == MessageSide.THEMSELVES) newMessage.user_id.toString() else newMessage.to_user_id.toString())

			response.enqueue(object : Callback<UserAPI> {
				override fun onResponse(
					call: Call<UserAPI>,
					response: Response<UserAPI>
				) {
					response.body()?.let {
						create(
							Conversation(
								to_user_id = if (side == MessageSide.THEMSELVES) newMessage.user_id else newMessage.to_user_id,
								last_message = newMessage.content,
								last_message_time = newMessage.created_at ?: Date(),
								unread_messages = 0,
								username = it.username,
								avatar = it.avatar ?: ""
							)
						)
					}
				}

				override fun onFailure(call: Call<UserAPI>, t: Throwable) {
					Timber.d(t)
				}

			})
		} else {
			conversationBox.query().run {
				(Conversation_.to_user_id equal if (side == MessageSide.THEMSELVES) newMessage.user_id else newMessage.to_user_id)
				build()
			}.use {
				val result = it.findFirst()
				if (result != null) {
					result.last_message = newMessage.content
					result.last_message_time = newMessage.created_at ?: Date()
					conversationBox.put(result)
				}
			}
		}
	}

	fun updateUserData(usersAPIWithoutCounters: List<UserAPIWithoutCounters>) {
		usersAPIWithoutCounters.map { user ->
			conversationBox.query().run {
				(Conversation_.to_user_id equal user.user_id)
				build()
			}.use {
				val result = it.findFirst()
				if (result != null) {
					result.username = user.username
					result.avatar = user.avatar ?: ""
					conversationBox.put(result)
				}
			}
		}
	}

	fun create(conversation: Conversation) {
		conversationBox.put(conversation)
	}

	fun delete(conversation_id: Long) {
		conversationBox.query().run {
			equal(Conversation_.id, conversation_id)
			build()
		}.use {
			it.remove()
		}
	}

	fun deleteByToUserIDs(user_ids: List<Long>) =

		conversationBox.query().run {
			`in`(Conversation_.to_user_id, user_ids.toLongArray())
			build()
		}.use { it ->
			it.remove()
			// Also remove history messages
			messageBox.query().run {
				(Message_.to_user_id oneOf user_ids.toLongArray()
						or (Message_.user_id oneOf user_ids.toLongArray()))
				build()
			}.use {
				it.remove()
			}
		}

	fun getAllUsers(): LongArray {
		val allConversations = conversationBox.all

		val usersIDs = allConversations.map { conversation ->
			conversation.to_user_id
		}

		return usersIDs.toLongArray()
	}
}