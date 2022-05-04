package net.teamof.whisper.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import net.teamof.whisper.api.GetMultipleUsers
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.models.UserAPIWithoutCounters
import net.teamof.whisper.repositories.ConversationRepository
import net.teamof.whisper.repositories.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

@HiltWorker
class CheckForNewProfilePhotos @AssistedInject constructor(
	@Assisted appContext: Context,
	@Assisted workerParams: WorkerParameters,
	private val usersAPI: UsersAPI,
	private val userRepository: UserRepository,
	private val conversationRepository: ConversationRepository
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

			response?.enqueue(object : Callback<List<UserAPIWithoutCounters>> {
				override fun onResponse(
					call: Call<List<UserAPIWithoutCounters>>,
					response: Response<List<UserAPIWithoutCounters>>
				) {
					response.body()?.let {
						userRepository.update(it)
						conversationRepository.updateUserData(it)
					}
				}

				override fun onFailure(call: Call<List<UserAPIWithoutCounters>>, t: Throwable) {
					Result.retry()
				}
			})

			Result.success()

		} catch (throwable: Throwable) {
			Timber.e(throwable)

			Result.retry()
		}
	}
}