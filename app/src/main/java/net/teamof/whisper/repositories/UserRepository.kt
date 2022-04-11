package net.teamof.whisper.repositories

import io.objectbox.Box
import io.objectbox.kotlin.equal
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.User
import net.teamof.whisper.models.UserAPIWithoutCounters
import net.teamof.whisper.models.User_

class UserRepository {
	private val userBox: Box<User> =
		ObjectBox.store.boxFor(User::class.java)

	fun update(users: List<UserAPIWithoutCounters>) {
		users.map { user ->
			userBox.query().run {
				(User_.user_id equal user.user_id)
				build()
			}.use {
				val result = it.findFirst()
				if (result != null) {
					result.username = user.username
					result.avatar = user.avatar ?: ""
					result.email = user.email
					result.profile.target.status = user.profile.status
					result.profile.target.description = user.profile.description
					userBox.put(result)
				}
			}
		}
	}
}