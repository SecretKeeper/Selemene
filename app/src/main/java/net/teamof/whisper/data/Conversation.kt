package net.teamof.whisper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "conversations")
data class Conversation(
	@PrimaryKey @ColumnInfo(name = "target_user")
	var target_user: Long = 0,
	@ColumnInfo(name = "username")
	var username: String,
	@ColumnInfo(name = "avatar")
	var avatar: String? = "",
	@ColumnInfo(name = "last_message")
	var last_message: String = "There is no messages",
	@ColumnInfo(name = "last_message_time")
	var last_message_time: Date = Date(),
	@ColumnInfo(name = "unread_messages")
	val unread_messages: Int = 0
)