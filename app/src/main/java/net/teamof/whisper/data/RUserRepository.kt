package net.teamof.whisper.data

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RUserRepository @Inject constructor(private val userDAO: UserDAO) {

	val readAllData: LiveData<List<User>> = userDAO.getAll()

	fun upsert(user: User) =
		userDAO.upsert(user)


	suspend fun getByUsername(username: String): User =
		userDAO.getByUsername(username)


	suspend fun updateUser(user: User) =
		userDAO.update(user)


	suspend fun deleteUser(user: User) =
		userDAO.delete(user)


}