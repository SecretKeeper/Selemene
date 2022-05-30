package net.teamof.whisper.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "profiles")
data class Profile(
	@PrimaryKey(autoGenerate = true) var profileId: Long = 0,
	val userId: Long,
	val status: String?,
	val description: String?,
)

data class UserWithProfileAndCounters(
	@Embedded val user: User,
	@Relation(
		entity = Profile::class,
		parentColumn = "userId",
		entityColumn = "userId"
	)
	val profile: ProfileWithCounters
)

