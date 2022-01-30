package net.teamof.whisper

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import net.teamof.whisper.api.AuthAPI
import net.teamof.whisper.api.RevokeTokenRequest
import timber.log.Timber

@HiltWorker
class RevokeTokenWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val authAPI: AuthAPI
) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {

        return try {
            val refreshToken = inputData.getString("refreshToken")

            if (refreshToken != null)
                authAPI.revokeToken(RevokeTokenRequest(refreshToken))

            Result.success()

        } catch (throwable: Throwable) {
            Timber.e(throwable)

            Result.retry()
        }
    }
}