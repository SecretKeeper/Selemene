package net.teamof.whisper.models

import kotlinx.serialization.Serializable

@Serializable
class WSSubscribeChannels(
    val userId: Long,
    val type: String,
    val channels: List<String>
)