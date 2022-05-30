package net.teamof.whisper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Contact(
	var id: Long = 0,
	val user_id: Long,
	val username: String,
	val avatar: String,
	val status: String = "None"
)
