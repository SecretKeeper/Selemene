package net.teamof.whisper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAPI(
	val user_id: Long,
	val username: String,
	val email: String,
	val avatar: String = "",
	val profile: Profile,
	val counters: Counters? // make it optional for now but in next steps must implement each service follower for own
)

@JsonClass(generateAdapter = true)
data class UserAPIWithoutCounters(
	val user_id: Long,
	val username: String,
	val email: String,
	val avatar: String = "",
	val profile: Profile,
)