package net.teamof.whisper.repositories

import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.Message_

class MessageRepository {

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

    fun create(message: Message) = messageBox.put((message))

    fun create(messages: List<Message>) = messageBox.put(messages)

    fun updateAssignedMessage(message: Message) {
        val query = messageBox.query().equal(Message_.local_id, message.local_id).build()
        val result = query.findFirst()

        if (result != null) {
            val uQuery = messageBox.get(result.local_id)
            uQuery.id = message.id
            uQuery.created_at = message.created_at
            messageBox.put(uQuery)
        }

        query.close()
    }
}