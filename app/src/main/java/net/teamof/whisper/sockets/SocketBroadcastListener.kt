package net.teamof.whisper.sockets

import android.app.ActivityManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.teamof.whisper.R
import net.teamof.whisper.data.ConversationRepository
import net.teamof.whisper.data.Message
import net.teamof.whisper.data.MessageRepository
import net.teamof.whisper.data.MessagesArray
import net.teamof.whisper.models.DeliveryReport
import net.teamof.whisper.models.MessageSide
import javax.inject.Inject

class SocketBroadcastListener @Inject constructor(
	val application: Application,
	val moshi: Moshi,
	val messageRepository: MessageRepository,
	val conversationRepository: ConversationRepository
) {

	val messageAdapter: JsonAdapter<Message> = moshi.adapter(Message::class.java)

	val arrayMessageAdapter: JsonAdapter<MessagesArray> =
		moshi.adapter(MessagesArray::class.java)

	val deliveryReportAdapter: JsonAdapter<DeliveryReport> =
		moshi.adapter(DeliveryReport::class.java)

	fun isAppRunning(context: Context, packageName: String): Boolean {
		val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
		val procInfos = activityManager.runningAppProcesses
		if (procInfos != null) {
			for (processInfo in procInfos) {
				if (processInfo.processName == packageName) {
					return true
				}
			}
		}
		return false
	}

	fun broadcastSubscribe(): Socket.OnEventListener =
		object : Socket.OnEventListener() {
			override fun onMessage(event: String?) {}
		}

	fun onMessageListener(): Socket.OnEventResponseListener =
		object : Socket.OnEventResponseListener() {
			override fun onMessage(event: String?, data: String?) {

				messageAdapter.fromJson(data)?.let {
					if (!isAppRunning(application, "net.teamof.whisper")) {
						CoroutineScope(Dispatchers.IO).launch {
							conversationRepository.updateConversationByReceivingMessage(
								MessageSide.THEMSELVES,
								it
							)
							messageRepository.upsert(it)
						}
						val builder = NotificationCompat.Builder(application, "MYWhisperCHANNEL_ID")
							.setSmallIcon(R.drawable.ic_checkmark)
							.setContentTitle("Whisper Messenger")
							.setContentText(it.content)
							.setPriority(NotificationCompat.PRIORITY_DEFAULT)

						val notificationManager =
							application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
						notificationManager.notify(it.sender.toInt(), builder.build())
					} else {
						application.sendBroadcast(
							Intent("WhisperLocalMessageCommunication").putExtra(
								"RECEIVE_MESSAGE",
								it
							)
						)
					}
				}
			}
		}

	fun onAssignedMessageListener(): Socket.OnEventResponseListener =
		object : Socket.OnEventResponseListener() {
			override fun onMessage(event: String?, data: String?) {
				messageAdapter.fromJson(data)?.let {
					if (!isAppRunning(application, "net.teamof.whisper")) {
						CoroutineScope(Dispatchers.IO).launch {
							messageRepository.upsert(it)
						}
					} else {
						application.sendBroadcast(
							Intent("WhisperLocalMessageCommunication").putExtra(
								"RECEIVE_ASSIGNED_MESSAGE",
								it
							)
						)
					}
				}
			}
		}

	fun onNewMessagesArrayListener(): Socket.OnEventResponseListener =
		object : Socket.OnEventResponseListener() {
			override fun onMessage(event: String?, data: String?) {
				Log.e("SocketBroadCastListener", "ArrayMessageListener: $data")
				arrayMessageAdapter.fromJson(data)?.let { it ->

					application.sendBroadcast(
						Intent("SEND_DELIVERY_REPORT").putExtra(
							"DELIVERY_REPORT_MODEL",
							DeliveryReport(ids = it.messages.map { message -> message.id })
						)
					)

					if (!isAppRunning(application, "net.teamof.whisper")) {
						CoroutineScope(Dispatchers.IO).launch {
							messageRepository.upsert(it.messages.toMutableList())
						}
						it.messages.map { message ->
							val builder =
								NotificationCompat.Builder(application, "MYWhisperCHANNEL_ID")
									.setSmallIcon(R.drawable.ic_flash_nav)
									.setContentTitle("Whisper Messenger")
									.setContentText(message.content)
									.setPriority(NotificationCompat.PRIORITY_DEFAULT)

							val notificationManager =
								application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
							notificationManager.notify(message.sender.toInt(), builder.build())
						}

					} else {
						it.messages.map { message ->
							application.sendBroadcast(
								Intent("WhisperLocalMessageCommunication").putExtra(
									"RECEIVE_MESSAGE",
									message
								)
							)
						}
					}
				}
			}
		}
}