package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Conversation

class ConversationsViewModel : ViewModel() {

    private val conversationBox: Box<Conversation> =
        ObjectBox.store.boxFor(Conversation::class.java)

    private val _conversations: MutableLiveData<MutableList<Conversation>> by lazy {
        MutableLiveData<MutableList<Conversation>>(loadMessages())
    }

    val conversations: MutableLiveData<MutableList<Conversation>> = _conversations

    private fun loadMessages(): MutableList<Conversation> {
        return conversationBox.all
    }

    fun createConversation(conversation: Conversation) {
        conversationBox.put(conversation)
    }

}