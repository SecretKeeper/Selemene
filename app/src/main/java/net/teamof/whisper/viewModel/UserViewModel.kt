package net.teamof.whisper.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.di.DataStoreManager
import net.teamof.whisper.models.Conversation
import net.teamof.whisper.models.WSSubscribeChannels
import net.teamof.whisper.utils.ScarletMessagingService
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val scarletMessagingService: ScarletMessagingService,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {

    private val conversationBox: Box<Conversation> =
        ObjectBox.store.boxFor(Conversation::class.java)

    fun getUserID(): LiveData<Long> {
        return dataStoreManager.getUserId().asLiveData()
    }

    suspend fun setUserID(userID: Long) {
        dataStoreManager.setUserId(userID)
    }

    suspend fun authenticate(username: String, password: String) {
//        setUserID(userID)
//        sendSubscribeChannels(userID)
    }

    private fun sendSubscribeChannels(userID: Long) {

        val channels = arrayListOf<String>()
        val existsChannels = conversationBox.all

        existsChannels.map { conversation -> channels.add(conversation.to_user_id.toString()) }

        scarletMessagingService.sendSubscribe(
            WSSubscribeChannels(
                userID,
                "subscribe-channels",
                channels
            )
        )
    }

}