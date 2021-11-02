package net.teamof.whisper.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.oneOf
import io.objectbox.kotlin.or
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.api.UserProfileResponse
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.models.Conversation_
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.Message_
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class ConversationsViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var usersAPI: UsersAPI

    private val conversationBox: Box<Conversation> =
        ObjectBox.store.boxFor(Conversation::class.java)

    private val messageBox: Box<Message> =
        ObjectBox.store.boxFor(Message::class.java)

    private var _conversations: ObjectBoxLiveData<Conversation> =
        ObjectBoxLiveData(conversationBox.query().build())

    val conversations: ObjectBoxLiveData<Conversation> = _conversations

    fun refreshConversations() {
        _conversations = ObjectBoxLiveData(conversationBox.query().build())
    }

    private fun isConversationExist(to_user_id: Long): Long =
        conversationBox.query().run {
            (Conversation_.to_user_id equal to_user_id)
            build()
        }.use { it.count() }

    fun getConversation(to_user_id: Long): Conversation? =
        conversationBox.query().run {
            equal(Conversation_.to_user_id, to_user_id)
            build()
        }.use { it.findFirst() }

    fun createConversation(conversation: Conversation) {
        if (isConversationExist(conversation.to_user_id) == 0L) {
            saveConversation(conversation) {
                refreshConversations()
            }
        }
    }

    private fun saveConversation(conversation: Conversation, refresh: () -> Unit) {
        conversationBox.put(conversation)
        refresh()
    }

    fun updateConversation(to_user_id: Long, newMessage: Message) {

        if (isConversationExist(to_user_id) == 0L) {
            val response = usersAPI.getUserProfile(newMessage.user_id)

            response.enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                    response.body()?.let {
                        createConversation(
                            Conversation(
                                to_user_id = newMessage.user_id,
                                last_message = newMessage.content,
                                last_message_time = newMessage.created_at,
                                unread_messages = 0,
                                username = it.username,
                                user_image = it.avatar
                            )
                        )
                    }
                    Timber.d(response.body().toString())
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    Timber.d(t)
                }

            })
        } else {
            conversationBox.query().run {
                (Conversation_.to_user_id equal to_user_id)
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

    fun deleteConversation(conversation: Long) {
        conversationBox.query().run {
            equal(Conversation_.id, conversation)
            build()
        }.use {
            it.remove()
            refreshConversations()
        }
    }

    fun deleteConversationsByToUserID(user_ids: List<Long>) =

        conversationBox.query().run {
            `in`(Conversation_.to_user_id, user_ids.toLongArray())
            build()
        }.use { it ->
            it.remove()
            refreshConversations()
            // Also remove history messages
            messageBox.query().run {
                (Message_.to_user_id oneOf user_ids.toLongArray()
                        or (Message_.user_id oneOf user_ids.toLongArray()))
                build()
            }.use {
                it.remove()
            }
        }
}