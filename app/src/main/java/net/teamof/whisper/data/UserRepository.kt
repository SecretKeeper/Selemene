package net.teamof.whisper.data

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userDAO: UserDAO) {

	val readAllData: LiveData<List<User>> = userDAO.getAll()

	fun upsert(pair: User) =
		userDAO.upsert(pair)

	fun upsert(pairs: MutableList<User>) =
		userDAO.upsert(pairs)

	fun getByUsername(username: String): User =
		userDAO.getByUsername(username)


	fun updateUser(user: User) =
		userDAO.update(user)


	fun deleteUser(user: User) =
		userDAO.delete(user)


}