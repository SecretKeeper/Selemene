package net.teamof.whisper.viewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.equal
import io.objectbox.kotlin.or
import io.objectbox.query.Query
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.*
import net.teamof.whisper.repositories.MessageRepository
import javax.inject.Inject

@HiltViewModel
open class MessagesViewModel
@Inject constructor(
    private val messageRepository: MessageRepository
) :
    ConversationsViewModel() {

    private val oBKeyValueBox: Box<OBKeyValue> = ObjectBox.store.boxFor(OBKeyValue::class.java)

    private val messageBox: Box<Message> = ObjectBox.store.boxFor(Message::class.java)

    private val currentUserId = oBKeyValueBox.query().run {
        equal(OBKeyValue_.key, "user_id")
        build()
    }.use { it.findFirst() }

    private var _messages: ObjectBoxLiveData<Message> =
        ObjectBoxLiveData(fetchAndObserveMessages())

    val messages: ObjectBoxLiveData<Message> = _messages

    private fun fetchAndObserveMessages(): Query<Message>? {
        val query = messageBox.query().build()

        query.subscribe().on(AndroidScheduler.mainThread()).observer {
            refreshConversations()
        }

        return query
    }

    fun sendMessage(message: Message) {

        messageRepository.saveMessage(message)

        updateConversation(MessageSide.MYSELF, message)

        _messages = ObjectBoxLiveData(messageBox.query().run {
            (Message_.to_user_id equal message.to_user_id
                    or (Message_.user_id equal message.user_id))
            orderDesc(Message_.id)
            build()
        })

//        scarletMessagingService.sendMessage(message)
    }

    fun saveMessage(messageSide: MessageSide, message: Message) {
        messageRepository.saveMessage((message))

        _messages = ObjectBoxLiveData(messageBox.query().run {
            (Message_.to_user_id equal message.to_user_id
                    or (Message_.user_id equal message.user_id))
            orderDesc(Message_.id)
            build()
        })
    }

    fun getConversationMessages(to_user_id: Long) {
        if (currentUserId != null) {
            ObjectBoxLiveData(
                messageBox.query().run {
                    (Message_.to_user_id equal to_user_id
                            or (Message_.user_id equal currentUserId.value))
                    order(Message_.created_at)
                    build()
                }
            )
        }
    }

}