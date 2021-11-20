package net.teamof.whisper.repositories

import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Message

class MessageRepository {

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

    fun create(message: Message) = messageBox.put((message))

}