package net.teamof.whisper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable
import java.util.*

@Entity(tableName = "messages")
@JsonClass(generateAdapter = true)
data class Message(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "local_id")
	var local_id: Long = 0,
	@ColumnInfo(name = "id")
	var id: Long = 0,
	@ColumnInfo(name = "user_id")
	var user_id: Long = 0,
	@ColumnInfo(name = "target_user")
	var target_user: Long = 0,
	@ColumnInfo(name = "content")
	var content: String = "",
	// this is not for first or second tick , this is just a helper to prevent sending request
	// server for
	@ColumnInfo(name = "verify_delivery_report")
	var verify_delivery_report: Boolean = false,
	@ColumnInfo(name = "created_at")
	var created_at: Date? = null,
	@ColumnInfo(name = "updated_at")
	var updated_at: Date? = null,
) : Serializable

@JsonClass(generateAdapter = true)
data class MessagesArray(
	val messages: List<Message>
)

