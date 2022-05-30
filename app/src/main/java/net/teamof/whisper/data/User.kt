package net.teamof.whisper.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "users")
data class User(
	@PrimaryKey var userId: Long,
	var username: String?,
	var email: String?,
	var avatar: String?
)