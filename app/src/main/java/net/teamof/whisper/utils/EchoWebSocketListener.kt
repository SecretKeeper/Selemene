package net.teamof.whisper.utils

import io.objectbox.Box
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.models.WSSubscribeChannels
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString


@OptIn(DelicateCoroutinesApi::class)
class EchoWebSocketListener :
    WebSocketListener() {

    private val webSocketMessageParser = WebSocketMessageParser()

    private val conversationBox: Box<Conversation> =
        ObjectBox.store.boxFor(Conversation::class.java)

    companion object {
        var webSocket: WebSocket? = null
        val NORMAL_CLOSURE_STATUS = 1000
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        val conversationsArray = arrayListOf<String>()
        conversationBox.all.map { conversation -> conversationsArray.add(conversation.channel) }



        webSocket.send(
            Json.encodeToString(
                WSSubscribeChannels(
                    8,
                    "subscribe-channels",
                    conversationsArray
                )
            )
        )

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        GlobalScope.launch {
            webSocketMessageParser.parseMessage(text)
        }
        println("Kotlin Receiving Text: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        println("Kotlin Receiving bytes: $bytes")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        println("Kotlin WebSocket Closing with code: $code and reason: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        println("Error: ${t.message}")
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

}