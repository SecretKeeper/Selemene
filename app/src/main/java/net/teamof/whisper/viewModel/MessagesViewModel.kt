package net.teamof.whisper.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.Message_

class MessagesViewModel constructor(
    private val channel: String
) :
    ViewModel() {

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

    private val _messages: MutableLiveData<MutableList<Message>> by lazy {
        MutableLiveData<MutableList<Message>>(loadMessages())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val messages: MutableLiveData<MutableList<Message>> = _messages

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(message: Message) {

        messageBox.put((message))

        _messages.value = (_messages.value)?.let { mutableListOf(*it.toTypedArray(), message) }
    }

    fun getLastMessage(): Message? {
        val query =
            messageBox.query().equal(Message_.channel, channel).orderDesc(Message_.id).build()
        val result = query.findFirst()
        query.close()

        return result
    }

    private fun loadMessages(): MutableList<Message> {
        val query = messageBox.query().equal(Message_.channel, channel).build()
        val results = query.find()
        query.close()

        return results
    }

}