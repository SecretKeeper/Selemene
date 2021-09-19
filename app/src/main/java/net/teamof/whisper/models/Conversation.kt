package net.teamof.whisper.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Conversation(
    @Id
    var id: Long = 0,
    var to_user_id: Long = 0,
    val username: String,
    val user_image: String,
    val last_message: String = "There is no messages",
    val last_message_time: String = "",
    val unread_messages: Int = 0
)
