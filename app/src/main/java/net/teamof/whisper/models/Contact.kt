package net.teamof.whisper.models

data class Contact(
    val user_id: Int,
    val username: String,
    val image: String,
    val status: String,
    val is_online: Boolean
)
