package net.teamof.whisper.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.teamof.whisper.api.GetMultipleUsers
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.data.Conversation
import net.teamof.whisper.data.User
import net.teamof.whisper.data.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@HiltWorker
class CheckForNewProfilePhotos @AssistedInject constructor(
	@Assisted appContext: Context,
	@Assisted workerParams: WorkerParameters,
	private val usersAPI: UsersAPI,
	private val conversationRepository: net.teamof.whisper.data.ConversationRepository,
	private val userRepository: UserRepository
) :
	CoroutineWorker(appContext, workerParams) {
	override suspend fun doWork(): Result {
		return try {
			val usersIdsToFetch = inputData.getLongArray("usersIdsToFetch")

			val response = usersIdsToFetch?.let {
				usersAPI.getMultipleUsers(
					GetMultipleUsers(it.toList())
				)
			}

			response?.enqueue(object : Callback<List<User>> {
				override fun onResponse(
					call: Call<List<User>>,
					response: Response<List<User>>
				) {
					CoroutineScope(Dispatchers.IO).launch {
						response.body()?.let {
							CoroutineScope(Dispatchers.IO).launch {
								conversationRepository.update(it.toMutableList() as MutableList<Conversation>)
								userRepository.upsert(it.toMutableList())
							}
						}
					}
				}

				override fun onFailure(call: Call<List<User>>, t: Throwable) {
					Result.retry()
				}
			})

			Result.success()

		} catch (throwable: Throwable) {
			Log.e("CheckForNewProfilePhoto.kt", throwable.toString())

			Result.retry()
		}
	}
}