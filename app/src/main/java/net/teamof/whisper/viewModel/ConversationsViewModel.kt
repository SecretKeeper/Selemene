package net.teamof.whisper.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.equal
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.api.UserProfileResponse
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.models.Conversation_
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.MessageSide
import net.teamof.whisper.repositories.ConversationRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository,
    private val usersAPI: UsersAPI
) : ViewModel() {

    private val conversationBox: Box<Conversation> =
        ObjectBox.store.boxFor(Conversation::class.java)

    private var _conversations: ObjectBoxLiveData<Conversation> =
        ObjectBoxLiveData(conversationBox.query().build())

    val conversations: ObjectBoxLiveData<Conversation> = _conversations

    fun refreshConversations() {
        _conversations = ObjectBoxLiveData(conversationBox.query().build())
    }

    fun createConversation(conversation: Conversation) =
        conversationRepository.create(conversation)

    fun getConversation(to_user_id: Long): Conversation? =
        conversationBox.query().run {
            equal(Conversation_.to_user_id, to_user_id)
            build()
        }.use { it.findFirst() }

    fun updateConversation(side: MessageSide, newMessage: Message) {
        if (conversationRepository.isConversationExist(
                when (side) {
                    MessageSide.THEMSELVES -> newMessage.user_id
                    MessageSide.MYSELF -> newMessage.to_user_id
                }
            ) == 0L
        ) {
            val response =
                usersAPI.getUserProfile(if (side == MessageSide.THEMSELVES) newMessage.user_id else newMessage.to_user_id)

            response.enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                    response.body()?.let {
                        conversationRepository.create(
                            Conversation(
                                to_user_id = if (side == MessageSide.THEMSELVES) newMessage.user_id else newMessage.to_user_id,
                                last_message = newMessage.content,
                                last_message_time = newMessage.created_at,
                                unread_messages = 0,
                                username = it.username,
                                user_image = it.avatar
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    Timber.d(t)
                }

            })
        } else {
            Timber.d("ITS EXECUTING!!!!")
            val query =
                conversationBox.query().run {
                    (Conversation_.to_user_id equal if (side == MessageSide.THEMSELVES) newMessage.user_id else newMessage.to_user_id)
                    build()
                }.use {
                    val result = it.findFirst()
                    if (result != null) {
                        result.last_message = newMessage.content
                        result.last_message_time = newMessage.created_at
                        conversationBox.put(result)
                    }
                }
        }
    }
}