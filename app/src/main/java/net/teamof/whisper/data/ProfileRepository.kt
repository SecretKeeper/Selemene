package net.teamof.whisper.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val profileDAO: ProfileDAO) {

	fun insert(profile: Profile) =
		profileDAO.insert(profile)

	fun upsert(profile: Profile) =
		profileDAO.upsert(profile)
	
	fun deleteByUserId(userId: Long) =
		profileDAO.deleteByUserId(userId)
}