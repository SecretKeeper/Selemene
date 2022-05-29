package net.teamof.whisper.workers

import android.content.Context
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import net.teamof.whisper.api.SetAvatarResponse
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.sharedprefrences.SharedPreferencesManagerImpl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@HiltWorker
class UpdateProfilePhoto @AssistedInject constructor(
	@Assisted appContext: Context,
	@Assisted workerParams: WorkerParameters,
	private val usersAPI: UsersAPI,
	private val sharedPreferencesManagerImpl: SharedPreferencesManagerImpl
) :
	CoroutineWorker(appContext, workerParams) {
	override suspend fun doWork(): Result {
		return try {
			val avatarUriString = inputData.getString("avatar_uri")!!

			val imageFileStream =
				applicationContext.contentResolver.openInputStream(
					avatarUriString.toUri()
				)

			val mime = MimeTypeMap.getSingleton()
			val type = mime.getExtensionFromMimeType(
				applicationContext.contentResolver.getType(avatarUriString.toUri())
			)

			val part = MultipartBody.Part.createFormData(
				"avatar", "avatar.${type}", RequestBody.create(
					"image/${type}".toMediaType(),
					imageFileStream!!.readBytes()
				)
			)

			val response =
				usersAPI.setAvatar(part, sharedPreferencesManagerImpl.getString("accessToken", ""))

			response.enqueue(object : Callback<SetAvatarResponse> {
				override fun onResponse(
					call: Call<SetAvatarResponse>,
					response: Response<SetAvatarResponse>
				) {
					response.body()?.let {
						sharedPreferencesManagerImpl.set("avatar", it.avatar)
					}
					Result.success()
				}

				override fun onFailure(call: Call<SetAvatarResponse>, t: Throwable) {
					Result.retry()
				}
			})

			Result.success()

		} catch (throwable: Throwable) {
			Result.retry()
		}
	}
}