package net.teamof.whisper.viewModel

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.teamof.whisper.data.ConversationRepository
import net.teamof.whisper.data.Message
import net.teamof.whisper.data.MessageRepository
import net.teamof.whisper.models.DeliveryReport
import net.teamof.whisper.models.MessageSide
import net.teamof.whisper.sharedprefrences.SharedPreferencesManagerImpl
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
				CoroutineScope(Dispatchers.IO).launch {
					conversationRepository.updateConversationByReceivingMessage(
						MessageSide.THEMSELVES,
						message
					)
					messageRepository.upsert(message)
				}
				application.sendBroadcast(
					Intent("SEND_DELIVERY_REPORT").putExtra(
						"DELIVERY_REPORT_MODEL",
						DeliveryReport(ids = listOf(message.id))
					)
				)
			}

			if (assignedMessage != null) messageRepository.upsert(assignedMessage)

		}
	}

	init {
		application.registerReceiver(
			broadcastReceiver,
			IntentFilter("WhisperLocalMessageCommunication")
		)
	}

	private val currentUserId = getCurrentUserId()

	private fun getCurrentUserId(): Long =
		sharedPreferencesManagerImpl.getLong("userId", 0L)

	fun sendMessage(message: Message) {

		CoroutineScope(Dispatchers.IO).launch {
			messageRepository.insert(message)
			conversationRepository.updateConversationByReceivingMessage(MessageSide.MYSELF, message)
		}

		application.sendBroadcast(
			Intent("SEND_MESSAGE").putExtra(
				"MESSAGE_MODEL",
				message
			)
		)
	}

	fun getConversationMessages(targetUserId: Long): LiveData<List<Message>> =
		messageRepository.getConversationMessages(targetUserId)

}