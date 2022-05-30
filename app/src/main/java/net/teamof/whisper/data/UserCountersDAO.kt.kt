package net.teamof.whisper.data

import androidx.room.*


@Dao
abstract class UserCountersDAO : BaseDao<UserCounters>() {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract override fun insert(obj: UserCounters): Long

	@Update
	abstract override fun update(obj: UserCounters)

	@Delete
	abstract override fun delete(obj: UserCounters)
}
