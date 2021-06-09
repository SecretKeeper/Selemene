package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.teamof.whisper.models.Message

class MessagesViewModel(username: String) : ViewModel() {


    private val _messages = MutableLiveData(
        mutableListOf(
            Message(
                1,
                1,
                "The veil between life and death",
                "2020-08-09",
                false
            ),
            Message(
                2,
                2,
                "Hello Phantom Assassin!",
                "2020-08-08",
                false
            ),
            Message(
                3,
                2,
                "That's Cool! I am user $username",
                "2020-08-08",
                false
            ),
            Message(
                4,
                1,
                "QQWEWRWQRWQEWQE",
                "2020-08-08",
                false
            ),
            Message(
                5,
                1,
                "EQWQw;fhdfhqwejkqleqwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
                "2020-08-08",
                false
            )
        )
    )

    val messages: MutableLiveData<MutableList<Message>> = _messages

    fun sendMessage(message: Message) {
        _messages.value = (_messages.value)?.let { mutableListOf(message, *it.toTypedArray()) }
    }

}