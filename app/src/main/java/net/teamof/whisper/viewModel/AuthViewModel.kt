package net.teamof.whisper.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.teamof.whisper.api.AuthAPI
import net.teamof.whisper.api.LoginRequest
import net.teamof.whisper.api.ProfileAPI
import net.teamof.whisper.screens.LoginButtonState
import net.teamof.whisper.sharedprefrences.SharedPreferencesManagerImpl
import net.teamof.whisper.workers.RevokeTokenWorker
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val authAPI: AuthAPI,
    private val profileAPI: ProfileAPI,
    private val sharedPreferences: SharedPreferencesManagerImpl
) :
    AndroidViewModel(application) {

    private val dataKeys = listOf(
        "access_token",
        "refresh_token",
        "expires",
        "user_id",
        "username",
        "email",
        "avatar"
    )

    private val workManager: WorkManager = WorkManager.getInstance(application)

    fun getUserID(): Long = sharedPreferences.getLong("userId", 0L)

    fun getUsername(): String = sharedPreferences.getString("username", "")

    fun getEmail(): String = sharedPreferences.getString("email", "")

    fun getAvatar(): String = sharedPreferences.getString("avatar", "")

    fun getStatus(): String = sharedPreferences.getString("status", "")

    fun getDescription(): String = sharedPreferences.getString("description", "")

    suspend fun login(
        navController: NavController,
        username: String,
        password: String,
        buttonState: (LoginButtonState) -> Unit,
    ) {
        buttonState(LoginButtonState(isLoading = true, isEnabled = false))

        CoroutineScope(Dispatchers.IO).launch {
            val response = authAPI.login(
                LoginRequest(
                    username,
                    password
                )
            )

            if (response.isSuccessful) {
                buttonState(
                    LoginButtonState(
                        isLoading = false,
                        isEnabled = false,
                        text = "Logging In..."
                    )
                )

                val jsonRes = JSONObject(response.body()?.string())

                saveUserData(jsonRes)

                withContext(Dispatchers.Main) {
                    navController.navigate("Conversations") {
                        launchSingleTop = true
                        popUpTo("Login") { inclusive = true }
                    }
                }
            } else {
                buttonState(
                    LoginButtonState(
                        isLoading = false,
                        isEnabled = false,
                        text = "Credentials Wrong",
                        btnColor = 0xFFe11d48
                    )
                )
                Timer().schedule(2500) {
                    buttonState(
                        LoginButtonState(
                            isLoading = false,
                            isEnabled = true,
                            text = "Login",
                            btnColor = 0xFF0336FF
                        )
                    )
                }
            }
        }
    }

    fun signOut(navController: NavController) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val builder = Data.Builder()
        builder.putString("refresh_token", sharedPreferences.getString("refresh_token", ""))

        val revokeTokenRequest = OneTimeWorkRequestBuilder<RevokeTokenWorker>()
            .setConstraints(constraints)
            .setInputData(builder.build())
            .build()

        workManager.beginUniqueWork("REVOKE_TOKEN", ExistingWorkPolicy.KEEP, revokeTokenRequest)
            .enqueue()


        // clear user data
        sharedPreferences.clear()

        navController.navigate("Login") {
            launchSingleTop = true
            popUpTo(0)
        }
    }

    private suspend fun getLoggedUserProfile(user_id: Long) {

        val response = profileAPI.getProfileByID(user_id)

        sharedPreferences.set("description", response.description ?: "")
        sharedPreferences.set("status", response.status ?: "")
    }

    private suspend fun saveUserData(userData: JSONObject) {

        dataKeys.map { sharedPreferences.set(it, userData.getString(it)) }

        // Fetch and save also user profile info
        getLoggedUserProfile(userData.getLong("user_id"))
    }
}
