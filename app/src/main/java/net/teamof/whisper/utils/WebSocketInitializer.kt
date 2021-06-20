package net.teamof.whisper.utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

@Module
@InstallIn(ActivityComponent::class)
object WebSocketInitializer {

    var WebSocket: WebSocket? = null

    private const val WebSocketPath = "ws://10.0.2.2:3334/ws"

    private val HttpClient = OkHttpClient()

    private val WebSocketListener = EchoWebSocketListener()

    @Provides
    fun initWebSocket(): WebSocket {
        val webSocket = HttpClient.newWebSocket(makeRequest(), WebSocketListener)

        HttpClient.dispatcher().executorService().shutdown()

        WebSocket = webSocket
        
        return webSocket
    }

    private fun makeRequest(): Request {
        return Request.Builder().url(WebSocketPath).build()
    }

}

@Module
@InstallIn(ViewModelComponent::class)
object WebSocketFetcher {
    @Provides
    fun getWebSocket(): WebSocket? = WebSocketInitializer.WebSocket
}
