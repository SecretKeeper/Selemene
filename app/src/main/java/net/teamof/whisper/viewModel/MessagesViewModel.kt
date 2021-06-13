package net.teamof.whisper.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.Message_

class MessagesViewModel(channel: Long) : ViewModel() {

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

    private val _messages: MutableLiveData<MutableList<Message>> by lazy {
        MutableLiveData<MutableList<Message>>(loadMessages(channel))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val messages: MutableLiveData<MutableList<Message>> = _messages

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(message: Message) {

        messageBox.put((message))

        _messages.value = (_messages.value)?.let { mutableListOf(*it.toTypedArray(), message) }
    }

    private fun loadMessages(user_id: Long): MutableList<Message> {
        val query = messageBox.query().equal(Message_.user_id, user_id).build()
        val results = query.find()
        query.close()

        return results
    }

}