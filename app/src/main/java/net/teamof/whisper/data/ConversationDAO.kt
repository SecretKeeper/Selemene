package net.teamof.whisper.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
abstract class ConversationDAO : BaseDao<Conversation>() {

	@Query("SELECT * FROM conversations")
	abstract fun getAllConversations(): LiveData<List<Conversation>>;

	@Query("SELECT * FROM conversations")
	abstract fun getAll(): List<Conversation>;

	@Query("SELECT COUNT(target_user) FROM conversations WHERE target_user = :target_user")
	abstract fun isConversationExist(target_user: Long): Long;

	@Query("SELECT * FROM conversations where username = :username")
	abstract fun getByUsername(username: String): Conversation

	@Query("SELECT * FROM conversations where target_user = :targetUser")
	abstract fun getConversationByUseId(targetUser: Long): Conversation

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract override fun insert(obj: Conversation): Long

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract override fun insert(obj: MutableList<Conversation>?): MutableList<Long>

	@Update
	abstract override fun update(obj: Conversation)

	@Update
	abstract override fun update(obj: MutableList<Conversation>?)

	@Delete
	abstract override fun delete(obj: Conversation)

	@Query("DELETE FROM conversations WHERE target_user IN (:obj)")
	abstract fun deleteByUserIds(obj: MutableList<Long>)
}
