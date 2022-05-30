package net.teamof.whisper.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.teamof.whisper.data.Conversation
import net.teamof.whisper.data.ConversationRepository
import net.teamof.whisper.workers.CheckForNewProfilePhotos
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
	application: Application,
	private val conversationRepository: ConversationRepository
) : AndroidViewModel(application) {

	private val workManager: WorkManager = WorkManager.getInstance(application)

	private var _conversations: LiveData<List<Conversation>> =
		fetchAndObserveConversations()

	val conversations: LiveData<List<Conversation>> = _conversations

	private fun fetchAndObserveConversations(): LiveData<List<Conversation>> {

		val conversations = conversationRepository.getAlLConversations

		CoroutineScope(Dispatchers.IO).launch {
			val constraints = Constraints.Builder()
				.setRequiredNetworkType(NetworkType.CONNECTED)
				.build()

			val builder = Data.Builder()


			val allConversations = conversationRepository.getAll()

			val usersIDs = allConversations.map { conversation ->
				conversation.target_user
			}

			builder.putLongArray("usersIdsToFetch", usersIDs.toLongArray())

			val revokeTokenRequest =
				PeriodicWorkRequestBuilder<CheckForNewProfilePhotos>(15, TimeUnit.MINUTES)
					.setConstraints(constraints)
					.setInputData(builder.build())
					.build()

			workManager.enqueueUniquePeriodicWork(
				"UPDATE_USER_DATA",
				ExistingPeriodicWorkPolicy.REPLACE,
				revokeTokenRequest
			)
		}

		return conversations
	}

}