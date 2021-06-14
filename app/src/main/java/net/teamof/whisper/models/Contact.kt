package net.teamof.whisper.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.annotation.Unique

@Entity
data class Contact(
    @Id
    var id: Long = 0,
    @Index @Unique val user_id: Long,
    val username: String,
    val user_image: String,
    val status: String,
    val is_online: Boolean,
    var selected: Boolean
)
