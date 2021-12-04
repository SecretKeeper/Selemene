package net.teamof.whisper.models

import com.squareup.moshi.JsonClass
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
@JsonClass(generateAdapter = true)
data class Message(
    @Id
    var id: Long = 0,
    var user_id: Long = 0,
    var to_user_id: Long = 0,
    var content: String = "",
    var created_at: Date = Date(),
    var updated_at: Date? = null,
)