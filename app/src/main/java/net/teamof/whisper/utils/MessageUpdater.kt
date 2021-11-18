package net.teamof.whisper.utils

import net.teamof.whisper.models.Message
import net.teamof.whisper.models.MessageSide
import net.teamof.whisper.repositories.MessageRepository
import net.teamof.whisper.viewModel.MessagesViewModel

class MessageUpdater(messageRepository: MessageRepository) :
    MessagesViewModel(messageRepository) {

    fun emitMessage(messageSide: MessageSide, message: Message) {
        saveMessage(messageSide, message)
        updateConversation(messageSide, message)
        refreshConversations()
    }
}