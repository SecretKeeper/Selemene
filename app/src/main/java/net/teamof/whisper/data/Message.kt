package net.teamof.whisper.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.util.*

@Entity(tableName = "messages", indices = [Index(value = ["id"])])
@JsonClass(generateAdapter = true)
data class Message(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "local_id")
    var localId: Long = 0,
    var id: Long = 0,
    var sender: Long = 0,
    var receiver: Long = 0,
    var content: String = "",
    // this is not for first or second tick , this is just a helper to prevent sending request
    // server for
    var verifyDeliveryReport: Boolean = false,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
) : Serializable

@JsonClass(generateAdapter = true)
data class MessagesArray(
    val messages: List<Message>
)

