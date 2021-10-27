package net.teamof.whisper.utils

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.WSSubscribeChannels

interface ScarletMessagingService {
    @Receive
    fun observeWebSocket(): Flow<WebSocket.Event>

    @Send
    fun sendSubscribe(subscribe: WSSubscribeChannels)

    @Send
    fun sendMessage(message: Message)

    @Receive
    fun observeMessage(): Flow<Message>
}