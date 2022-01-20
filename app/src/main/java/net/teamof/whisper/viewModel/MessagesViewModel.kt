package net.teamof.whisper.viewModel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.or
import io.objectbox.query.Query
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.*
import net.teamof.whisper.repositories.ConversationRepository
import net.teamof.whisper.repositories.MessageRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val application: Application,
    private val messageRepository: MessageRepository,
    private val conversationRepository: ConversationRepository
) : ViewModel() {

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val message = intent.getSerializableExtra("RECEIVE_MESSAGE") as? Message

            if (message != null) {
                conversationRepository.update(MessageSide.THEMSELVES, message)
                messageRepository.create(message)
            }


            Timber.d("We Go It in VM onReceive: $message")
        }
    }

    init {
        application.registerReceiver(
            broadcastReceiver,
            IntentFilter("WhisperLocalMessageCommunication")
        )
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
            Timber.d("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ ")
        }

        return query
    }

    fun sendMessage(message: Message) {

        messageRepository.create(message)

        application.sendBroadcast(
            Intent("WhisperLocalMessageCommunication").putExtra(
                "SEND_MESSAGE",
                message
            )
        )
    }

    fun getConversationMessages(to_user_id: Long) {
        if (currentUserId != null) {
            ObjectBoxLiveData(
                messageBox.query().run {
                    (Message_.to_user_id equal to_user_id
                            or (Message_.user_id equal currentUserId.value))
                    order(Message_.id)
                    build()
                }
            )
        }
    }

}