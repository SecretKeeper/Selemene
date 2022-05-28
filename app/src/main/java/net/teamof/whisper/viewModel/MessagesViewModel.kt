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
import net.teamof.whisper.models.DeliveryReport
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.MessageSide
import net.teamof.whisper.models.Message_
import net.teamof.whisper.repositories.ConversationRepository
import net.teamof.whisper.repositories.MessageRepository
import net.teamof.whisper.sharedprefrences.SharedPreferencesManagerImpl
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
	private val application: Application,
	private val messageRepository: MessageRepository,
	private val conversationRepository: ConversationRepository,
	private val sharedPreferencesManagerImpl: SharedPreferencesManagerImpl
) : ViewModel() {

	private val broadcastReceiver = object : BroadcastReceiver() {
		override fun onReceive(context: Context, intent: Intent) {
			val message = intent.getSerializableExtra("RECEIVE_MESSAGE") as? Message

			val assignedMessage =
				intent.getSerializableExtra("RECEIVE_ASSIGNED_MESSAGE") as? Message

			if (message != null) {
				conversationRepository.update(MessageSide.THEMSELVES, message)
				messageRepository.create(message)
				application.sendBroadcast(
					Intent("SEND_DELIVERY_REPORT").putExtra(
						"DELIVERY_REPORT_MODEL",
						DeliveryReport(ids = listOf(message.id))
					)
				)
			}

			if (assignedMessage != null) messageRepository.updateAssignedMessage(assignedMessage)

		}
	}

	init {
		application.registerReceiver(
			broadcastReceiver,
			IntentFilter("WhisperLocalMessageCommunication")
		)
	}

	private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

	private val currentUserId = getCurrentUserId()

	private fun getCurrentUserId(): Long =
		sharedPreferencesManagerImpl.getLong("userId", 0L)

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

		conversationRepository.update(MessageSide.MYSELF, message)

		application.sendBroadcast(
			Intent("SEND_MESSAGE").putExtra(
				"MESSAGE_MODEL",
				message
			)
		)
	}

	fun getConversationMessages(to_user_id: Long) {
		_messages = ObjectBoxLiveData(
			messageBox.query().run {
				(Message_.content equal to_user_id
						or (Message_.user_id equal currentUserId))
				order(Message_.id)
				build()
			}
		)
	}
}