package net.teamof.whisper.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
abstract class MessageDAO : BaseDao<Message>() {

    @Query("SELECT * FROM messages")
    abstract fun getAllMessages(): LiveData<List<Message>>

    @Query("SELECT * FROM messages WHERE receiver = :targetUserId OR sender = :targetUserId ORDER BY createdAt")
    abstract fun getMessagesByTargetUserId(targetUserId: Long): LiveData<List<Message>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: Message): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: MutableList<Message>?): MutableList<Long>

    @Update
    abstract override fun update(obj: Message)

    @Update
    abstract override fun update(obj: MutableList<Message>?)

    @Query("DELETE FROM messages WHERE localId = (:id)")
    abstract fun deleteMessageById(id: Long)
}
