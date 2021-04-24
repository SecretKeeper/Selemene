package net.teamof.whisper.models

data class MessagePortal(
    val user_id: Int,
    val username: String,
    val image: String,
    val last_message: String,
    val last_message_time: String,
    val unread_messages: Int
)
