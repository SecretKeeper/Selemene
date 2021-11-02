package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData

class ConversationActionsViewModel : ConversationsViewModel() {

    private val _showActionsState: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val showActionsState: MutableLiveData<Boolean> = _showActionsState

    private val _selectedConversations: MutableLiveData<List<Long>> by lazy {
        MutableLiveData<List<Long>>(mutableListOf())
    }

    val selectedConversations: MutableLiveData<List<Long>> = _selectedConversations


    fun showActions(conversation_id: Long) {
        _selectedConversations.value = (_selectedConversations.value)?.let {
            mutableListOf(
                *it.toTypedArray(),
                conversation_id
            )
        }
        _showActionsState.value = true
    }

    fun hideActions() {
        _selectedConversations.value = mutableListOf()
        _showActionsState.value = false
    }

    fun selectConversationByToUserID(user_id: Long) {
        _selectedConversations.value = (_selectedConversations.value)?.let {
            mutableListOf(
                *it.toTypedArray(),
                user_id
            )
        }
    }

    fun unselectConversationByToUserID(user_id: Long) {
        _selectedConversations.value =
            _selectedConversations.value?.filter { it != user_id }
    }

    fun deleteSelectedConversations() {
        if (_selectedConversations.value != null)
            deleteConversationsByToUserID(_selectedConversations.value!!)
    }
}