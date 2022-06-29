package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.teamof.whisper.data.ConversationRepository
import javax.inject.Inject

@HiltViewModel
class ConversationActionsViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository
) : ViewModel() {

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
            CoroutineScope(Dispatchers.IO).launch {
                conversationRepository.deleteByUserIds(_selectedConversations.value!!.toMutableList())
            }
    }
}