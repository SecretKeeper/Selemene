package net.teamof.whisper.data

import androidx.room.*

@Dao
abstract class KeyValueDAO : BaseDao<KeyValue>() {

	@Query("SELECT * FROM keys where key = :key LIMIT 1")
	abstract fun getByKey(key: String): KeyValue

	@Query("SELECT * FROM keys WHERE key IN (:keys)")
	abstract fun loadAllByKeys(keys: List<String>): List<KeyValue>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract override fun insert(obj: KeyValue): Long

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract override fun insert(obj: MutableList<KeyValue>?): MutableList<Long>

	@Update
	abstract override fun update(obj: KeyValue)

	@Update
	abstract override fun update(obj: MutableList<KeyValue>?)

	@Delete
	abstract override fun delete(obj: KeyValue)

	@Query("DELETE FROM keys")
	abstract fun deleteAllKeys()
}
