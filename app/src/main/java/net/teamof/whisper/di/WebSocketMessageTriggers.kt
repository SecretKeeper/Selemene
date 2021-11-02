package net.teamof.whisper.di

import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.OBKeyValue
import net.teamof.whisper.models.OBKeyValue_
import net.teamof.whisper.models.WSSubscribeChannels
import net.teamof.whisper.utils.ScarletMessagingService
import javax.inject.Inject

class WebSocketMessageTriggers @Inject constructor(
    private val scarletMessagingService: ScarletMessagingService
) {

    private val oBKeyValueBox: Box<OBKeyValue> = ObjectBox.store.boxFor(OBKeyValue::class.java)

    private val currentUserId = oBKeyValueBox.query().run {
        equal(OBKeyValue_.key, "user_id")
        build()
    }.use { it.findFirst() }

    fun sendSubscribeChannels() {

        val channels = arrayListOf<String>()

        if (currentUserId != null) {
            channels.add(currentUserId.value)

            scarletMessagingService.sendSubscribe(
                WSSubscribeChannels(
                    currentUserId.value,
                    "subscribe-channels",
                    channels
                )
            )
        }
    }

    fun sendSubscribeChannels(user_id: String) {

        val channels = arrayListOf<String>()

        channels.add(user_id)

        scarletMessagingService.sendSubscribe(
            WSSubscribeChannels(
                user_id,
                "subscribe-channels",
                channels
            )
        )
    }
}