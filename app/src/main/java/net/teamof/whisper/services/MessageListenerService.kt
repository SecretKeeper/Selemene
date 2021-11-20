package net.teamof.whisper.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.*
import dagger.hilt.android.AndroidEntryPoint
import net.teamof.whisper.models.*
import net.teamof.whisper.sockets.Socket
import net.teamof.whisper.sockets.SocketBroadcastListener
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MessageListenerService : Service() {

    @Inject
    lateinit var whisperSocket: Socket

    @Inject
    lateinit var socketBroadcastListener: SocketBroadcastListener
    
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): MessageListenerService = this@MessageListenerService
    }

    override fun onCreate() {
        super.onCreate()

        whisperSocket.onEvent(Socket.EVENT_OPEN, socketBroadcastListener.broadcastSubscribe())
        whisperSocket.onEventResponse("message", socketBroadcastListener.onMessageListener())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    @SuppressLint("CheckResult")
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}