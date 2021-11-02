package net.teamof.whisper.viewModel

import androidx.lifecycle.viewModelScope
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.or
import io.objectbox.query.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.di.WebSocketMessageTriggers
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

    @Inject
    lateinit var webSocketMessageTriggers: WebSocketMessageTriggers

    init {

        scarletMessagingService.observeWebSocket().flowOn(Dispatchers.IO).onEach {
            when (it) {
                is WebSocket.Event.OnConnectionOpened<*> -> webSocketMessageTriggers.sendSubscribeChannels()
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
                updateConversation(MessageSide.THEMSELVES, it)
            }
            .launchIn(viewModelScope)
    }

    private val oBKeyValueBox: Box<OBKeyValue> = ObjectBox.store.boxFor(OBKeyValue::class.java)

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

    private val currentUserId = oBKeyValueBox.query().run {
        equal(OBKeyValue_.key, "user_id")
        build()
    }.use { it.findFirst() }

    private var _messages: ObjectBoxLiveData<Message> =
        ObjectBoxLiveData(fetchAndObserveMessages())

    val messages: ObjectBoxLiveData<Message> = _messages

    private fun fetchAndObserveMessages(): Query<Message>? {
        val query = messageBox.query().build()

        query.subscribe().on(AndroidScheduler.mainThread()).observer {
            refreshConversations()
        }

        return query
    }

    fun sendMessage(message: Message) {

        messageRepository.saveMessage(message)

        updateConversation(MessageSide.MYSELF, message)

        _messages = ObjectBoxLiveData(messageBox.query().run {
            (Message_.to_user_id equal message.to_user_id
                    or (Message_.user_id equal message.user_id))
            orderDesc(Message_.id)
            build()
        })

        scarletMessagingService.sendMessage(message)
    }

    private fun saveMessage(message: Message) {
        messageRepository.saveMessage((message))

        _messages = ObjectBoxLiveData(messageBox.query().run {
            (Message_.to_user_id equal message.to_user_id
                    or (Message_.user_id equal message.user_id))
            orderDesc(Message_.id)
            build()
        })
    }

    fun getConversationMessages(to_user_id: Long) {
        if (currentUserId != null) {
            ObjectBoxLiveData(
                messageBox.query().run {
                    (Message_.to_user_id equal to_user_id
                            or (Message_.user_id equal currentUserId.value))
                    order(Message_.created_at)
                    build()
                }
            )
        }
    }

}