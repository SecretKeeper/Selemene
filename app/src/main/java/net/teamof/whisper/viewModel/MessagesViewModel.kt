package net.teamof.whisper.viewModel

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.or
import io.objectbox.query.Query
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.Message_
import net.teamof.whisper.models.OBKeyValue
import net.teamof.whisper.models.OBKeyValue_
import net.teamof.whisper.repositories.MessageRepository
import net.teamof.whisper.sockets.Socket
import net.teamof.whisper.sockets.SocketBroadcastListener
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    moshi: Moshi,
    socketBroadcastListener: SocketBroadcastListener,
    private val whisperSocket: Socket,
    private val messageRepository: MessageRepository,
) : ViewModel() {

    init {
        whisperSocket.onEvent(Socket.EVENT_OPEN, socketBroadcastListener.broadcastSubscribe())
        whisperSocket.onEventResponse("message", socketBroadcastListener.onMessageListener())
    }

    val messageAdapter = moshi.adapter(Message::class.java)

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
            Timber.d("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ ")
        }

        return query
    }

    fun sendMessage(message: Message) {

        messageRepository.create(message)

        _messages = ObjectBoxLiveData(messageBox.query().run {
            (Message_.to_user_id equal message.to_user_id
                    or (Message_.user_id equal message.user_id))
            orderDesc(Message_.id)
            build()
        })

        whisperSocket.send("message", messageAdapter.toJson(message))
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