package net.teamof.whisper.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.equal
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.models.Conversation_
import net.teamof.whisper.models.Message
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class ConversationsViewModel @Inject constructor() : ViewModel() {

    private val conversationBox: Box<Conversation> =
        ObjectBox.store.boxFor(Conversation::class.java)

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
            Timber.d("We Created a new conversation Sorry! ${isConversationExist(to_user_id)}")
            createConversation(
                Conversation(
                    to_user_id = newMessage.to_user_id,
                    last_message = newMessage.content,
                    last_message_time = newMessage.created_at,
                    unread_messages = 0,
                    username = "The Trick is Working",
                    user_image = "https://cdn-icons-png.flaticon.com/128/924/924874.png"
                )
            )
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

    fun deleteConversation(conversations: List<Long>) =

        conversationBox.query().run {
            `in`(Conversation_.id, conversations.toLongArray())
            build()
        }.use {
            it.remove()
            refreshConversations()
        }
}