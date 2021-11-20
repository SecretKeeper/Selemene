package net.teamof.whisper.repositories

import io.objectbox.Box
import io.objectbox.kotlin.oneOf
import io.objectbox.kotlin.or
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.models.Conversation_
import net.teamof.whisper.models.Message
import net.teamof.whisper.models.Message_

class ConversationRepository {
    private val conversationBox: Box<Conversation> =
        ObjectBox.store.boxFor(Conversation::class.java)

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)


    fun isConversationExist(to_user_id: Long): Long {
        val query = conversationBox.query().equal(Conversation_.to_user_id, to_user_id).build()
        val result = query.count()
        query.close()

        return result
    }


    fun create(conversation: Conversation) {
        conversationBox.put(conversation)
    }

    fun delete(conversation_id: Long) {
        conversationBox.query().run {
            equal(Conversation_.id, conversation_id)
            build()
        }.use {
            it.remove()
//            refreshConversations()
        }
    }

    fun deleteByToUserIDs(user_ids: List<Long>) =

        conversationBox.query().run {
            `in`(Conversation_.to_user_id, user_ids.toLongArray())
            build()
        }.use { it ->
            it.remove()
//            refreshConversations()
            // Also remove history messages
            messageBox.query().run {
                (Message_.to_user_id oneOf user_ids.toLongArray()
                        or (Message_.user_id oneOf user_ids.toLongArray()))
                build()
            }.use {
                it.remove()
            }
        }
}