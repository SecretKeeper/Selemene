package net.teamof.whisper.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MessagesViewModelFactory(private val channel: Long) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MessagesViewModel(channel) as T
}