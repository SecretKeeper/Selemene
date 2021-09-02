package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.Message_
import net.teamof.whisper.models.WSSubscribeChannels
import net.teamof.whisper.utils.DateMoshiAdapter
import net.teamof.whisper.utils.ScarletMessagingService
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel
@Inject constructor(private val scarletMessagingService: ScarletMessagingService) :
    ViewModel() {

    init {
        scarletMessagingService.observeWebSocket()
            .flowOn(Dispatchers.IO)
            .onEach { event ->
                if (event is WebSocket.Event.OnConnectionOpened<*>) {
                    scarletMessagingService.sendSubscribe(
                        WSSubscribeChannels(
                            8,
                            "subscribe-channels",
                            arrayListOf("OK", "Qwerty")
                        )
                    )
                }
            }
            .launchIn(viewModelScope)

        scarletMessagingService.observeMessage()
            .flowOn(Dispatchers.IO)
            .onEach {
                saveMessage(it)
                Timber.d("WTF A MESSAGE FROM SERVER = $it")
            }
            .launchIn(viewModelScope)

    }

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

    private val moshiMessageAdapter =
        Moshi.Builder()
            .add(Date::class.java, DateMoshiAdapter().nullSafe())
            .addLast(KotlinJsonAdapterFactory())
            .build().adapter(Message::class.java)

    private val _messages: MutableLiveData<MutableList<Message>> by lazy {
        MutableLiveData<MutableList<Message>>()
    }

    val messages: MutableLiveData<MutableList<Message>> = _messages

    fun sendMessage(message: Message) {

        saveMessage(message)

        message.updated_at = Date()

        scarletMessagingService.sendMessage(message)
    }

    private fun saveMessage(message: Message) {
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