package net.teamof.whisper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "users")
data class User(
	@PrimaryKey @ColumnInfo(name = "user_id") val userId: Long,
	@ColumnInfo(name = "username") val username: String?,
	@ColumnInfo(name = "email") val email: String?,
	@ColumnInfo(name = "avatar") val avatar: String?
)