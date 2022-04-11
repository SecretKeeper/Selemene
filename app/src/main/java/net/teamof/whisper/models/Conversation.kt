package net.teamof.whisper.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import java.util.*

@Entity
data class Conversation(
	@Id
	var id: Long = 0,
	@Unique
	var to_user_id: Long = 0,
	@Unique
	var username: String,
	var avatar: String,
	var last_message: String = "There is no messages",
	var last_message_time: Date = Date(),
	val unread_messages: Int = 0
)
