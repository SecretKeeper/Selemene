package net.teamof.whisper.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.models.Conversation_
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

    private fun isConversationExist(conversation: Conversation): Long {
        val query =
            conversationBox.query().equal(Conversation_.to_user_id, conversation.to_user_id).build()
        val result = query.count()
        query.close();

        return result
    }

    fun getConversation(to_user_id: Long): Conversation? =
        conversationBox.query().run {
            equal(Conversation_.to_user_id, to_user_id)
            build()
        }.use { it.findFirst() }


    fun createConversation(conversation: Conversation) {
        if (isConversationExist(conversation) == 0L) {
            saveConversation(conversation) {
                refreshConversations()
            }
        }
    }

    private fun saveConversation(conversation: Conversation, refresh: () -> Unit) {
        conversationBox.put(conversation)
        refresh()
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