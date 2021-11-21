package net.teamof.whisper.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.query.Query
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.models.Conversation_
import net.teamof.whisper.repositories.ConversationRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository
) : ViewModel() {

    private val conversationBox: Box<Conversation> =
        ObjectBox.store.boxFor(Conversation::class.java)

    private var _conversations: ObjectBoxLiveData<Conversation> =
        ObjectBoxLiveData(fetchAndObserveConversations())

    val conversations: ObjectBoxLiveData<Conversation> = _conversations

    private fun fetchAndObserveConversations(): Query<Conversation>? {
        val query = conversationBox.query().build()

        query.subscribe().on(AndroidScheduler.mainThread()).observer {
            Timber.d("CONVERSATIONS UPDATED!!! WTF ")
        }

        return query
    }

    fun createConversation(conversation: Conversation) =
        conversationRepository.create(conversation)

    fun getConversation(to_user_id: Long): Conversation? =
        conversationBox.query().run {
            equal(Conversation_.to_user_id, to_user_id)
            build()
        }.use { it.findFirst() }
}