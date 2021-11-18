package net.teamof.whisper.services

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.R
import net.teamof.whisper.models.*
import net.teamof.whisper.models.Message
import net.teamof.whisper.sockets.Socket
import net.teamof.whisper.utils.MessageUpdater
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MessageListenerService : Service() {

    val context = this

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var messageUpdater: MessageUpdater

    private val oBKeyValueBox: Box<OBKeyValue> = ObjectBox.store.boxFor(OBKeyValue::class.java)

    private val currentUserId = oBKeyValueBox.query().run {
        equal(OBKeyValue_.key, "user_id")
        build()
    }.use { it.findFirst() }

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): MessageListenerService = this@MessageListenerService
    }

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

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()


        val channelId =
            "all_notifications" // You should create a String resource for this instead of storing in a variable
        val descriptionText = "Really Lmao"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", channelId, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        //

//
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(7845, builder.build())

//        scarletMessagingService.sendSubscribe(
//            WSSubscribeChannels(
//                "2",
//                "subscribe-channels",
//                channels
//            )
//        )
//
//


//        scarletMessagingService.observeMessage().subscribe {
//            messageBox.put(it)
//        }
    }

    @SuppressLint("CheckResult")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val subscribeAdapter = moshi.adapter(WSSubscribeChannels::class.java)
        val messageAdapter = moshi.adapter(Message::class.java)

        val socket =
            Socket.Builder.with("ws://10.0.2.2:3335/ws")
                .addHeader("user-id", currentUserId?.value ?: "0")
                .build().connect()
        val socketEventListener = object : Socket.OnEventListener() {
            override fun onMessage(event: String?) {
                Timber.e("Socket OPen Happened")

                val channels = listOf("2")

                socket.send(
                    "subscribe-channels", subscribeAdapter.toJson(
                        WSSubscribeChannels(
                            "2",
                            channels
                        )
                    )
                )
            }
        }


        socket.onEvent(Socket.EVENT_OPEN, socketEventListener)
        socket.onEventResponse("message", object : Socket.OnEventResponseListener() {
            override fun onMessage(event: String?, data: String?) {
                messageAdapter.fromJson(data)?.let {
                    messageUpdater.emitMessage(MessageSide.THEMSELVES, it)
                    if (!isAppRunning(context, "net.teamof.whisper")) {
                        val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
                            .setSmallIcon(R.drawable.objectbox_notification)
                            .setContentTitle("Whisper Messenger")
                            .setContentText(it.content)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                        val notificationManager =
                            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.notify(7845, builder.build())
                    }
                }
            }

        })

        return START_STICKY
    }

    @SuppressLint("CheckResult")
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}