package net.teamof.whisper.repositories

import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.OBKeyValue
import net.teamof.whisper.models.OBKeyValue_

class KeyValueRepository {
    private val oBKeyValueBox: Box<OBKeyValue> =
        ObjectBox.store.boxFor(OBKeyValue::class.java)

    fun create(pair: OBKeyValue) {
        oBKeyValueBox.put(pair)
    }

    fun getLoggedUser(): Long {
        val userId = oBKeyValueBox.query().run {
            equal(OBKeyValue_.key, "user_id")
            build()
        }.use { it.findFirst() }

        return userId?.value?.toLong() ?: 0L
    }

//    fun delete(conversation_id: Long) {
//        conversationBox.query().run {
//            equal(Conversation_.id, conversation_id)
//            build()
//        }.use {
//            it.remove()
//        }
//    }
//
//    fun deleteByToUserIDs(user_ids: List<Long>) =
//
//        conversationBox.query().run {
//            `in`(Conversation_.to_user_id, user_ids.toLongArray())
//            build()
//        }.use { it ->
//            it.remove()
//            // Also remove history messages
//            messageBox.query().run {
//                (Message_.to_user_id oneOf user_ids.toLongArray()
//                        or (Message_.user_id oneOf user_ids.toLongArray()))
//                build()
//            }.use {
//                it.remove()
//            }
//        }
}