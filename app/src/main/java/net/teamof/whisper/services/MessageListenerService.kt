package net.teamof.whisper.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.AndroidEntryPoint
import net.teamof.whisper.models.*
import net.teamof.whisper.models.Message
import net.teamof.whisper.sockets.Socket
import net.teamof.whisper.sockets.SocketBroadcastListener
import net.teamof.whisper.utils.DateMoshiAdapter
import timber.log.Timber
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


    inner class LocalBinder : Binder() {
        fun getService(): MessageListenerService = this@MessageListenerService
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            whisperSocket.send(
                "message",
                moshiMessageAdapter.toJson(intent.getSerializableExtra("SEND_MESSAGE") as Message?)
            )
            Timber.d(
                "Oh Well Oh well onReceive: ${
                    intent.getSerializableExtra(
                        "SEND_MESSAGE"
                    )
                }"
            )
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "WhisperChannelName"
            val description = "channel_desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("MYWhisperCHANNEL_ID", name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        Timber.d("I AM REGISTERInG IT")
        registerReceiver(broadcastReceiver, IntentFilter("WhisperLocalMessageCommunication"))

        whisperSocket.onEvent(Socket.EVENT_OPEN, socketBroadcastListener.broadcastSubscribe())
        whisperSocket.onEventResponse("message", socketBroadcastListener.onMessageListener())
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