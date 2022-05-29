package net.teamof.whisper.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
abstract class UserDAO : BaseDao<User>() {

	@Query("SELECT * FROM users")
	abstract fun getAll(): LiveData<List<User>>;

	@Query("SELECT * FROM users where username = :username")
	abstract fun getByUsername(username: String): User

	@Query("SELECT * FROM users WHERE user_id IN (:userIds)")
	abstract fun loadAllByIds(userIds: IntArray): List<User>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract override fun insert(obj: User): Long

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract override fun insert(obj: MutableList<User>?): MutableList<Long>

	@Update
	abstract override fun update(obj: User)

	@Update
	abstract override fun update(obj: MutableList<User>?)

	@Delete
	abstract override fun delete(obj: User)
}
