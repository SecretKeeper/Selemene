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

	fun getPair(key: String): OBKeyValue? {
		val query = oBKeyValueBox.query(OBKeyValue_.key equal key).build()
		val result = query.findFirst()
		query.close()

		return result
	}

	fun getToken(): String {
		val query = oBKeyValueBox.query(OBKeyValue_.key equal "accessToken").build()
		val result = query.findFirst()
		query.close()

		return result?.value ?: ""
	}

	fun setAvatar(avatar: String) {
		val query = oBKeyValueBox.query(OBKeyValue_.key equal "avatar").build()
		val result = query.findFirst()

		if (result != null) {
			val uQuery = oBKeyValueBox.get(result.id)
			uQuery.value = avatar
			oBKeyValueBox.put(uQuery)
		} else
			oBKeyValueBox.put(OBKeyValue(key = "avatar", value = avatar))
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

	private fun createOrUpdatedSpecificKey(key: String, value: String) {
		val query = oBKeyValueBox.query(OBKeyValue_.key equal key).build()
		val result = query.findFirst()

		if (result != null) {
			val uQuery = oBKeyValueBox.get(result.id)
			uQuery.value = value
			oBKeyValueBox.put(uQuery)
		} else
			oBKeyValueBox.put(OBKeyValue(key = key, value = value))

		query.close()
	}
}