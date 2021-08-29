package net.teamof.whisper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WSSubscribeChannels(
    val userId: Long,
    val type: String,
    val channels: List<String>
)