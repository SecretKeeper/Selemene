package net.teamof.whisper.utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

@Module
@InstallIn(ViewModelComponent::class)
object WebSocketInitializer {

    private const val WebSocketPath = "ws://10.0.2.2:3334/ws"

    private val HttpClient = OkHttpClient()

    private val WebSocketListener = EchoWebSocketListener()

    @Provides
    fun WebSocket(): WebSocket {
        val webSocket = HttpClient.newWebSocket(makeRequest(), WebSocketListener)

        HttpClient.dispatcher().executorService().shutdown()

        return webSocket
    }

    private fun makeRequest(): Request {
        return Request.Builder().url(WebSocketPath).build()
    }

}