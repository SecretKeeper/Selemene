package net.teamof.whisper.models

import com.squareup.moshi.JsonClass
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.annotation.Unique
import io.objectbox.relation.ToOne
import io.realm.RealmDictionary
import io.realm.RealmObject

@Entity
@JsonClass(generateAdapter = true)
data class User(
	@Id
	var id: Long = 0,
	@Index @Unique var user_id: Long = 0,
	var username: String = "",
	var email: String = "",
	var avatar: String = ""
) {
	lateinit var profile: ToOne<Profile>
	lateinit var counters: ToOne<Counters>
}

@Entity
@JsonClass(generateAdapter = true)
data class Profile(
	@Id var id: Long = 0,
	var description: String = "",
	var status: String = ""
)

@Entity
@JsonClass(generateAdapter = true)
data class Counters(
	@Id var id: Long = 0,
	var feeds: Int = 0,
	var followers: Int = 0
)

open class RUser : RealmObject() {
	var userId: String? = null
	var username: String? = null
	var email: String? = null
	var avatar: String? = null
	var accessToken: String? = null
	var refreshToken: String? = null
	var accessTokenExpiresAt: String? = null
	var trophies: RealmDictionary<String> = RealmDictionary()
}