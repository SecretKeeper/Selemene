package net.teamof.whisper.repositories

import io.objectbox.Box
import io.objectbox.kotlin.equal
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.OBKeyValue
import net.teamof.whisper.models.OBKeyValue_

class KeyValueRepository {
    private val oBKeyValueBox: Box<OBKeyValue> =
        ObjectBox.store.boxFor(OBKeyValue::class.java)

    fun create(pair: OBKeyValue) {
        oBKeyValueBox.put(pair)
    }

    fun createOrUpdate(pair: OBKeyValue) {
        val query = oBKeyValueBox.query(OBKeyValue_.key equal pair.key).build()
        val result = query.findFirst()

        if (result != null) {
            val uQuery = oBKeyValueBox.get(result.id)
            uQuery.value = pair.value
            oBKeyValueBox.put(uQuery)
        } else
            oBKeyValueBox.put(pair)
    }

    fun createOrUpdate(pairs: List<OBKeyValue>) {

        pairs.map { pair ->
            val query = oBKeyValueBox.query(OBKeyValue_.key equal pair.key).build()
            val result = query.findFirst()

            if (result != null) {
                val uQuery = oBKeyValueBox.get(result.id)
                uQuery.value = pair.value
                oBKeyValueBox.put(uQuery)
            } else
                oBKeyValueBox.put(pair)
        }
    }

    fun getLoggedUser(): Long {
        val query = oBKeyValueBox.query(OBKeyValue_.key equal "user_id").build()
        val result = query.findFirst()
        query.close()

        return result?.value?.toLong() ?: 0L
    }

    fun getToken(): String {
        val query = oBKeyValueBox.query(OBKeyValue_.key equal "token").build()
        val result = query.findFirst()
        query.close()

        return result?.value ?: ""
    }

    fun delete(key: String) {
        val query = oBKeyValueBox.query(OBKeyValue_.key equal key).build()
        query.remove()

        query.close()
    }

    fun delete(keys: List<String>) {

        keys.map { key ->
            val query = oBKeyValueBox.query(OBKeyValue_.key equal key).build()
            query.remove()

            query.close()
        }

    }
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