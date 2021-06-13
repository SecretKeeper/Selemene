package net.teamof.whisper.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Conversation(
    @Id
    var id: Long = 0,
    val user_id: Long,
    val username: String,
    val user_image: String,
    val last_message: String,
    val last_message_time: String,
    val unread_messages: Int
)
