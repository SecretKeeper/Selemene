package net.teamof.whisper.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class OBKeyValue(
    @Id
    var id: Long = 0,
    var key: String,
    var value: String
)
