package net.teamof.whisper.utils

import com.tinder.scarlet.websocket.WebSocketEvent
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.WSSubscribeChannels

interface ScarletMessagingService {
    @Receive
    fun observeWebSocket(): Flow<WebSocketEvent>

    @Send
    fun sendSubscribe(subscribe: WSSubscribeChannels)

    @Send
    fun sendMessage(message: Message)

    @Receive
    fun observeMessage(): Flow<Message>
}