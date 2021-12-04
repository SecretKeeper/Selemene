package net.teamof.whisper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAPI(
    val user_id: Long,
    val username: String,
    val email: String,
    val avatar: String,
    val profile: Profile,
    val _count: Counters
)