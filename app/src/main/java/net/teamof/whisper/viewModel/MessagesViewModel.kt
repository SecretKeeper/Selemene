package net.teamof.whisper.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.Message_

class MessagesViewModel : ViewModel() {

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

    private val _messages: MutableLiveData<MutableList<Message>> by lazy {
        MutableLiveData<MutableList<Message>>()
    }

    val messages: MutableLiveData<MutableList<Message>> = _messages

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(message: Message) {

        messageBox.put((message))

        _messages.value = (_messages.value)?.let { mutableListOf(*it.toTypedArray(), message) }
    }

    fun getLastMessage(channel: String): Message? {
        val query =
            messageBox.query().equal(Message_.channel, channel).orderDesc(Message_.id).build()
        val result = query.findFirst()
        query.close()

        return result
    }

    fun getMessagesByChannel(channel: String) {
        val query =
            messageBox.query().equal(Message_.channel, channel).order(Message_.created_at)
                .build()
        val result = query.find()
        query.close()

        _messages.value = result
    }

}