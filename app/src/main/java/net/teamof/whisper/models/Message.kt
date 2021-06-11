package net.teamof.whisper.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Message(
    @Id
    var id: Long = 0,
    var user_id: Long,
    val content: String,
    var created_at: String
)