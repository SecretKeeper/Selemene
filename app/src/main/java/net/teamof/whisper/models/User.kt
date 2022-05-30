package net.teamof.whisper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
	var id: Long = 0,
	var user_id: Long = 0,
	var username: String = "",
	var email: String = "",
	var avatar: String = ""
)

@JsonClass(generateAdapter = true)
data class Profile(
	var id: Long = 0,
	var description: String = "",
	var status: String = ""
)

@JsonClass(generateAdapter = true)
data class Counters(
	var id: Long = 0,
	var feeds: Int = 0,
	var followers: Int = 0
)
