package net.teamof.whisper.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class Message(
    @Id
    var id: Long = 0,
    var channel: String = "",
    var user_id: Long = 0,
    var content: String = "",
    var created_at: Date = Date(),
    var updated_at: Date? = null
)