package net.teamof.whisper.data

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(private val messageDAO: MessageDAO) {

	val getAllMessages: LiveData<List<Message>> =
		messageDAO.getAllMessages()

	fun getConversationMessages(targetUserId: Long) =
		messageDAO.getMessagesByTargetUserId(targetUserId)

	fun insert(message: Message) =
		messageDAO.insert(message)

	fun insert(messages: MutableList<Message>) =
		messageDAO.insert(messages)

	fun upsert(message: Message) =
		messageDAO.upsert(message)

	fun upsert(messages: MutableList<Message>) =
		messageDAO.upsert(messages)


	fun updateUser(message: Message) =
		messageDAO.update(message)


	fun deleteUser(message: Message) =
		messageDAO.delete(message)


}