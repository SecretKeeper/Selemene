package net.teamof.whisper.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "user_counters")
data class UserCounters(
	@PrimaryKey(autoGenerate = true) var userCountersId: Long = 0,
	val userId: Long,
	val feeds: Long?,
	val followers: Long?,
)

data class ProfileWithCounters(
	@Embedded val profile: Profile,
	@Relation(
		parentColumn = "userId",
		entityColumn = "userId",
	)
	val userCounters: UserCounters
)