package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.or
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.*
import net.teamof.whisper.repositories.MessageRepository
import net.teamof.whisper.utils.ScarletMessagingService
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel
@Inject constructor(
    private val scarletMessagingService: ScarletMessagingService,
    private val messageRepository: MessageRepository
) :
    ConversationsViewModel() {

    init {

        scarletMessagingService.observeWebSocket().flowOn(Dispatchers.IO).onEach {
            when (it) {
                is WebSocket.Event.OnConnectionOpened<*> -> sendSubscribeChannels()
                is WebSocket.Event.OnConnectionClosing -> Timber.d("Socket Connection closing")
                is WebSocket.Event.OnConnectionClosed -> Timber.d("Socket Connection closed")
                is WebSocket.Event.OnConnectionFailed -> Timber.e(it.throwable)
                is WebSocket.Event.OnMessageReceived -> Timber.d("Socket Message Received ${it.message}")
                else -> Timber.d("Socket $it")
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

    private val currentUserId = OBKeyValueBox.query().run {
        equal(OBKeyValue_.key, "user_id")
        build()
    }.use { it.findFirst() }

    private val _messages: MutableLiveData<MutableList<Message>> by lazy {
        MutableLiveData<MutableList<Message>>()
    }

    val messages: MutableLiveData<MutableList<Message>> = _messages

    private fun sendSubscribeChannels() {

        val channels = arrayListOf<String>()

        if (currentUserId != null) {
            channels.add(currentUserId.value)

            scarletMessagingService.sendSubscribe(
                WSSubscribeChannels(
                    currentUserId.value,
                    "subscribe-channels",
                    channels
                )
            )
        }
    }

    fun sendMessage(message: Message) {

        messageRepository.saveMessage(message)

        _messages.value = (_messages.value)?.let { mutableListOf(*it.toTypedArray(), message) }

        scarletMessagingService.sendMessage(message)
    }

    private fun saveMessage(message: Message) {
        messageRepository.saveMessage((message))

        refreshConversations()

        _messages.value = (_messages.value)?.let { mutableListOf(*it.toTypedArray(), message) }
    }

    fun getLastMessage(to_user_id: Long): Message? {
        if (currentUserId != null)
            return messageBox.query().run {
                (Message_.to_user_id equal to_user_id
                        or (Message_.user_id equal currentUserId.value))
                orderDesc(Message_.id)
                build()
            }.use { it.findFirst() }

        return null
    }

    fun getConversationMessages(to_user_id: Long) {
        if (currentUserId != null) {

            val messages =
                messageBox.query().run {
                    (Message_.to_user_id equal to_user_id
                            or (Message_.user_id equal currentUserId.value))
                    order(Message_.created_at)
                    build()
                }.use { it.find() }

            _messages.value = messages
        }
    }

}