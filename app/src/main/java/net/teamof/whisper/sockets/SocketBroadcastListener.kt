package net.teamof.whisper.sockets

import android.app.ActivityManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import net.teamof.whisper.R
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.MessageSide
import net.teamof.whisper.models.WSSubscribeChannels
import net.teamof.whisper.repositories.ConversationRepository
import net.teamof.whisper.repositories.KeyValueRepository
import net.teamof.whisper.repositories.MessageRepository
import timber.log.Timber
import javax.inject.Inject

class SocketBroadcastListener @Inject constructor(
    val application: Application,
    val whisperSocket: Socket,
    val moshi: Moshi,
    val messageRepository: MessageRepository,
    val conversationRepository: ConversationRepository,
    val keyValueRepository: KeyValueRepository
) {

    val subscribeAdapter: JsonAdapter<WSSubscribeChannels> =
        moshi.adapter(WSSubscribeChannels::class.java)

    val messageAdapter: JsonAdapter<Message> = moshi.adapter(Message::class.java)

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
            override fun onMessage(event: String?) {
                Timber.e("Socket OPen Happened")

                val channels = listOf(keyValueRepository.getLoggedUser().toString())

                whisperSocket.send(
                    "subscribe-channels", subscribeAdapter.toJson(
                        WSSubscribeChannels(
                            keyValueRepository.getLoggedUser().toString(),
                            channels
                        )
                    )
                )
            }
        }

    fun onMessageListener(): Socket.OnEventResponseListener =
        object : Socket.OnEventResponseListener() {
            override fun onMessage(event: String?, data: String?) {

                messageAdapter.fromJson(data)?.let {
                    if (!isAppRunning(application, "net.teamof.whisper")) {
                        conversationRepository.update(MessageSide.THEMSELVES, it)
                        messageRepository.create(it)

                        val builder = NotificationCompat.Builder(application, "MYWhisperCHANNEL_ID")
                            .setSmallIcon(R.drawable.objectbox_notification)
                            .setContentTitle("Whisper Messenger")
                            .setContentText(it.content)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                        val notificationManager =
                            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.notify(it.user_id.toInt(), builder.build())
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
}