package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.models.Conversation_
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor() : ViewModel() {

    private val conversationBox: Box<Conversation> =
        ObjectBox.store.boxFor(Conversation::class.java)

    private val _conversations: MutableLiveData<MutableList<Conversation>> by lazy {
        MutableLiveData<MutableList<Conversation>>(conversationBox.all)
    }

    val conversations: MutableLiveData<MutableList<Conversation>> = _conversations

    private fun refreshConversations() {
        _conversations.value = conversationBox.all
    }

    private fun isConversationExist(conversation: Conversation): Long {
        val query =
            conversationBox.query().equal(Conversation_.to_user_id, conversation.to_user_id).build()
        val result = query.count()
        query.close();

        return result
    }

    fun getConversation(to_user_id: Long): Conversation? {
        val query = conversationBox.query().equal(Conversation_.to_user_id, to_user_id).build()
        val result = query.findFirst()
        query.close()

        return result
    }

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

    fun deleteConversation() {
        conversationBox.removeAll()
        refreshConversations()
    }

    fun deleteConversation(conversation: Array<Conversation>) {

    }
}