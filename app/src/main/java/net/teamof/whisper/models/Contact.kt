package net.teamof.whisper.models

import com.squareup.moshi.JsonClass
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.annotation.Unique

@Entity
@JsonClass(generateAdapter = true)
data class Contact(
    @Id
    var id: Long = 0,
    @Index @Unique val user_id: Long,
    val username: String,
    val avatar: String,
    val status: String = "None"
)
