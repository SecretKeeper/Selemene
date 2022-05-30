package net.teamof.whisper.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCountersRepository @Inject constructor(private val userCountersDAO: UserCountersDAO) {

	fun insert(userCounters: UserCounters) =
		userCountersDAO.insert(userCounters)

	fun upsert(userCounters: UserCounters) =
		userCountersDAO.upsert(userCounters)

	fun delete(userCounters: UserCounters) =
		userCountersDAO.delete(userCounters)
}