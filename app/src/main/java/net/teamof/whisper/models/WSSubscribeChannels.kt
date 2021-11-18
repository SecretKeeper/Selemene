package net.teamof.whisper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WSSubscribeChannels(
    val user_id: String = "0",
    val channels: List<String>
)