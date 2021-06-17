package net.teamof.whisper.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.serialization.Serializable
import net.teamof.whisper.utils.DateAsStringSerializer
import java.util.*

@Entity
@Serializable
data class Message(
    @Id
    var id: Long = 0,
    var channel: String = "",
    var user_id: Long = 0,
    var content: String = "",
    @Serializable(with = DateAsStringSerializer::class)
    var created_at: Date = Date(),
    @Serializable(with = DateAsStringSerializer::class)
    var updated_at: Date? = null
)