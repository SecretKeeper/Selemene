package net.teamof.whisper.data

import androidx.room.*


@Dao
abstract class ProfileDAO : BaseDao<Profile>() {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract override fun insert(obj: Profile): Long

	@Update
	abstract override fun update(obj: Profile)

	@Query("DELETE FROM profiles WHERE userId = (:userId)")
	abstract fun deleteByUserId(userId: Long)
}
