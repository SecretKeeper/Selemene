package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.*
import net.teamof.whisper.utils.ScarletMessagingService
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel
@Inject constructor(
    private val scarletMessagingService: ScarletMessagingService
) :
    ViewModel() {

    init {

        scarletMessagingService.observeWebSocket().flowOn(Dispatchers.IO).onEach {
            when (it) {
                is WebSocket.Event.OnConnectionOpened<*> -> sendSubscribeChannels()
                is WebSocket.Event.OnConnectionClosing -> Timber.d(
                    "Socket",
                    "Connection closing"
                )
                is WebSocket.Event.OnConnectionClosed -> Timber.d("Socket", "Connection closed")
                is WebSocket.Event.OnConnectionFailed -> Timber.e(
                    "Socket",
                    "Connection failed",
                    it.throwable
                )
                is WebSocket.Event.OnMessageReceived -> Timber.d("Socket", "Message received")
                else -> Timber.d("Socket", it.toString())
            }
        }.launchIn(viewModelScope)

        scarletMessagingService.observeMessage()
            .flowOn(Dispatchers.IO)
            .onEach {
                saveMessage(it)
                Timber.d("WTF A MESSAGE FROM SERVER = $it")
            }
            .launchIn(viewModelScope)
    }

    private val OBKeyValueBox: Box<OBKeyValue> = ObjectBox.store.boxFor(OBKeyValue::class.java)

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

    private val _messages: MutableLiveData<MutableList<Message>> by lazy {
        MutableLiveData<MutableList<Message>>()
    }

    val messages: MutableLiveData<MutableList<Message>> = _messages

    private fun sendSubscribeChannels() {

        val channels = arrayListOf<String>()

        val query = OBKeyValueBox.query().equal(OBKeyValue_.key, "user_id").build()
        val result = query.findFirst()
        query.close()

        if (result != null) {
            channels.add(result.value)

            scarletMessagingService.sendSubscribe(
                WSSubscribeChannels(
                    result.value,
                    "subscribe-channels",
                    channels
                )
            )
        }
    }

    fun sendMessage(message: Message) {

        saveMessage(message)

        scarletMessagingService.sendMessage(message)
    }

    private fun saveMessage(message: Message) {
        messageBox.put((message))

        _messages.value = (_messages.value)?.let { mutableListOf(*it.toTypedArray(), message) }
    }

    fun getLastMessage(to_user_id: Long): Message? {
        val query =
            messageBox.query().equal(Message_.to_user_id, to_user_id).orderDesc(Message_.id).build()
        val result = query.findFirst()
        query.close()

        return result
    }

    fun getMessagesByChannel(to_user_id: Long) {
        val query =
            messageBox.query().equal(Message_.to_user_id, to_user_id).order(Message_.created_at)
                .build()
        val result = query.find()
        query.close()

        _messages.value = result
    }

}