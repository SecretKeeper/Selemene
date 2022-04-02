package net.teamof.whisper.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import net.teamof.whisper.models.DeliveryReport
import net.teamof.whisper.models.Message
import net.teamof.whisper.sockets.Socket
import net.teamof.whisper.sockets.SocketBroadcastListener
import net.teamof.whisper.utils.DateMoshiAdapter
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MessageListenerService : Service() {

    @Inject
    lateinit var whisperSocket: Socket

    @Inject
    lateinit var socketBroadcastListener: SocketBroadcastListener

    private val moshi = Moshi.Builder().add(Date::class.java, DateMoshiAdapter()).addLast(
        KotlinJsonAdapterFactory()
    ).build()

    private val binder = LocalBinder()

    private val moshiMessageAdapter: JsonAdapter<Message> = moshi.adapter(Message::class.java)

    private val moshiDeliveryReportAdapter: JsonAdapter<DeliveryReport> =
        moshi.adapter(DeliveryReport::class.java)

    inner class LocalBinder : Binder() {
        fun getService(): MessageListenerService = this@MessageListenerService
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                "SEND_MESSAGE" -> {
                    whisperSocket.send(
                        "message",
                        moshiMessageAdapter.toJson(intent.getSerializableExtra("MESSAGE_MODEL") as Message?)
                    )
                }
                "SEND_DELIVERY_REPORT" -> {
                    whisperSocket.send(
                        "delivery-report",
                        moshiDeliveryReportAdapter.toJson(intent.getSerializableExtra("DELIVERY_REPORT_MODEL") as DeliveryReport?)
                    )
                }
            }
        }
    }

    private fun createNotificationChannel() {
        val name: CharSequence = "WhisperChannelName"
        val description = "channel_desc"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("MYWhisperCHANNEL_ID", name, importance)
        channel.description = description
        
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        registerReceiver(broadcastReceiver, IntentFilter().apply {
            addAction("SEND_MESSAGE")
            addAction("SEND_DELIVERY_REPORT")
        })

        whisperSocket.onEvent(Socket.EVENT_OPEN, socketBroadcastListener.broadcastSubscribe())

        whisperSocket.onEventResponse("message", socketBroadcastListener.onMessageListener())

        whisperSocket.onEventResponse(
            "assigned_message",
            socketBroadcastListener.onAssignedMessageListener()
        )

    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    @SuppressLint("CheckResult")
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}