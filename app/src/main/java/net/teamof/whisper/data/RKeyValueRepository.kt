package net.teamof.whisper.data

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RKeyValueRepository @Inject constructor(private val keyValueDAO: KeyValueDAO) {
	fun upsert(pair: KeyValue) =
		keyValueDAO.upsert(pair)

	fun upsert(pairs: MutableList<KeyValue>) =
		keyValueDAO.upsert(pairs)


	fun getByKey(key: String): KeyValue =
		keyValueDAO.getByKey(key)


	fun updatePair(pair: KeyValue) =
		keyValueDAO.update(pair)


	fun deletePair(pair: KeyValue) =
		keyValueDAO.delete(pair)


	fun deleteAllKeys() =
		keyValueDAO.deleteAllKeys()

}