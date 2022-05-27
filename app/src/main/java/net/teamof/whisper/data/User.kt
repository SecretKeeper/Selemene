package net.teamof.whisper.data

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "users")
data class User(
	@PrimaryKey @ColumnInfo(name = "user_id") val userId: Long,
	@ColumnInfo(name = "username") val username: String?,
	@ColumnInfo(name = "email") val email: String?,
	@ColumnInfo(name = "avatar") val avatar: String?
)