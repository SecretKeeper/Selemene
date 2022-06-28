package net.teamof.whisper.viewModel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.teamof.whisper.api.*
import net.teamof.whisper.data.ProfileRepository
import net.teamof.whisper.data.UserRepository
import net.teamof.whisper.models.UserAPI
import net.teamof.whisper.screens.settings.myAccount.ChangeEmailButtonState
import net.teamof.whisper.screens.settings.myAccount.ChangePasswordButtonState
import net.teamof.whisper.screens.settings.myAccount.ChangeStatusButtonState
import net.teamof.whisper.screens.settings.myAccount.ChangeUsernameButtonState
import net.teamof.whisper.sharedprefrences.SharedPreferencesManagerImpl
import net.teamof.whisper.ui.theme.AccentGreenLong
import net.teamof.whisper.workers.UpdateProfilePhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class UserViewModel @Inject constructor(
    application: Application,
    private val authAPI: AuthAPI,
    private val searchAPI: SearchAPI,
    private val accountAPI: AccountAPI,
    private val profileAPI: ProfileAPI,
    private val usersAPI: UsersAPI,
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferencesManagerImpl
) :
    AndroidViewModel(application) {

    private val workManager: WorkManager = WorkManager.getInstance(application)

    fun getUserID(): Long = sharedPreferences.getLong("user_id", 0L)

    fun getUsername(): String = sharedPreferences.getString("username", "")

    fun getEmail(): String = sharedPreferences.getString("email", "")

    fun getAvatar(): String = sharedPreferences.getString("avatar", "")

    fun getStatus(): String = sharedPreferences.getString("status", "")

    fun getDescription(): String = sharedPreferences.getString("description", "")

    suspend fun changePassword(
        current_password: String,
        new_password: String,
        buttonState: (ChangePasswordButtonState) -> Unit,
    ) {
        buttonState(ChangePasswordButtonState(isLoading = true, isEnabled = false))

        CoroutineScope(Dispatchers.IO).launch {
            val response = accountAPI.changePassword(
                ChangePasswordRequest(
                    current_password,
                    new_password
                )
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    buttonState(
                        ChangePasswordButtonState(
                            isLoading = false,
                            isEnabled = false,
                            text = "Password Change Successfully",
                            btnColor = AccentGreenLong
                        )
                    )

                    Timer().schedule(2000) {
                        buttonState(
                            ChangePasswordButtonState(
                                isLoading = false,
                                isEnabled = true,
                                text = "Change Password",
                                btnColor = 0xFF0336FF
                            )
                        )
                    }
                } else {
                    buttonState(
                        ChangePasswordButtonState(
                            isLoading = false,
                            isEnabled = false,
                            text = "Credentials Wrong",
                            btnColor = 0xFFe11d48
                        )
                    )
                    Timer().schedule(2500) {
                        buttonState(
                            ChangePasswordButtonState(
                                isLoading = false,
                                isEnabled = true,
                                text = "Change Password",
                                btnColor = 0xFF0336FF
                            )
                        )
                    }
                }
            }
        }
    }

    suspend fun changeUsername(
        newUsername: String,
        current_password: String,
        buttonState: (ChangeUsernameButtonState) -> Unit,
    ) {
        buttonState(ChangeUsernameButtonState(isLoading = true, isEnabled = false))

        CoroutineScope(Dispatchers.IO).launch {
            val response = accountAPI.changeUsername(
                ChangeUsernameRequest(
                    newUsername,
                    current_password
                )
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    buttonState(
                        ChangeUsernameButtonState(
                            isLoading = false,
                            isEnabled = false,
                            text = "Username Changed",
                            btnColor = AccentGreenLong
                        )
                    )

                    if (response.code() == 200) sharedPreferences.set("username", newUsername)

                    Timer().schedule(2000) {
                        buttonState(
                            ChangeUsernameButtonState(
                                isEnabled = true,
                                text = "Username Username",
                                btnColor = 0xFF0336FF
                            )
                        )
                    }
                } else {
                    buttonState(
                        ChangeUsernameButtonState(
                            isLoading = false,
                            isEnabled = false,
                            text = "Credentials Wrong",
                            btnColor = 0xFFe11d48
                        )
                    )
                    Timer().schedule(2500) {
                        buttonState(
                            ChangeUsernameButtonState(
                                isEnabled = true,
                                text = "Credentials Username",
                                btnColor = 0xFF0336FF
                            )
                        )
                    }
                }
            }
        }
    }

    suspend fun changeEmail(
        email: String,
        password: String,
        buttonState: (ChangeEmailButtonState) -> Unit,
    ) {
        buttonState(ChangeEmailButtonState(isLoading = true, isEnabled = false))

        CoroutineScope(Dispatchers.IO).launch {
            val response = accountAPI.changeEmail(
                ChangeEmailRequest(
                    email,
                    password
                )
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    buttonState(
                        ChangeEmailButtonState(
                            isLoading = false,
                            isEnabled = false,
                            text = "Done.."
                        )
                    )
                } else {
                    buttonState(
                        ChangeEmailButtonState(
                            isLoading = false,
                            isEnabled = false,
                            text = "Credentials Wrong",
                            btnColor = 0xFFe11d48
                        )
                    )
                    Timer().schedule(2500) {
                        buttonState(
                            ChangeEmailButtonState(
                                isEnabled = true,
                                text = "Change Email",
                                btnColor = 0xFF0336FF
                            )
                        )
                    }
                }
            }
        }
    }

    suspend fun signupWithEmailPassword(
        completeRegistration: (Boolean) -> Unit,
        username: String,
        email: String,
        password: String,
        buttonLoading: (Boolean) -> Unit,
        buttonText: (String) -> Unit,
        buttonColor: (Long) -> Unit,
        buttonEnabled: (Boolean) -> Unit
    ) {
        buttonLoading(true)
        buttonEnabled(false)

        CoroutineScope(Dispatchers.IO).launch {
            val response = authAPI.signup(
                SignupRequest(
                    username,
                    email,
                    password
                )
            )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    buttonLoading(false)
                    buttonEnabled(false)
                    //buttonColor(0xff3ddc84)
                    buttonText("Your account successfully created")
                    completeRegistration(true)

                } else {
                    buttonLoading(false)
                    buttonEnabled(false)
                    buttonText("Credentials Wrong")
                    buttonColor(0xFFe11d48)
                    Timer().schedule(2500) {
                        buttonColor(0xFF0336FF)
                        buttonText("Signup")
                        buttonEnabled(true)
                    }
                }
            }
        }
    }

    suspend fun setStatus(
        newStatus: String,
        buttonState: (ChangeStatusButtonState) -> Unit,
    ) {
        buttonState(ChangeStatusButtonState(isLoading = true, isEnabled = false))

        CoroutineScope(Dispatchers.IO).launch {
            val response = profileAPI.setStatus(
                SetStatus(status = newStatus)
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    buttonState(
                        ChangeStatusButtonState(
                            isLoading = false,
                            isEnabled = false,
                            text = "Status Updated",
                            btnColor = AccentGreenLong
                        )
                    )

                    if (response.code() == 200) sharedPreferences.set("status", newStatus)

                    Timer().schedule(2000) {
                        buttonState(
                            ChangeStatusButtonState(
                                isEnabled = true,
                                text = "Set Status",
                                btnColor = 0xFF0336FF
                            )
                        )
                    }
                } else {
                    buttonState(
                        ChangeStatusButtonState(
                            isLoading = false,
                            isEnabled = false,
                            text = "Credentials Wrong",
                            btnColor = 0xFFe11d48
                        )
                    )
                    Timer().schedule(2500) {
                        buttonState(
                            ChangeStatusButtonState(
                                isLoading = true,
                                text = "Set Status",
                                btnColor = 0xFF0336FF
                            )
                        )
                    }
                }
            }
        }
    }

    private suspend fun getLoggedUserProfile(user_id: Long) {

        val response = profileAPI.getProfileByID(user_id)

        sharedPreferences.set("description", response.description ?: "")
        sharedPreferences.set("status", response.status ?: "")
    }

    fun searchUsers(input: String, fetchedUsers: (List<UserAPI>) -> Unit) {

        val response = searchAPI.searchUsers(input)

        response.enqueue(object : Callback<List<UserAPI>> {
            override fun onResponse(
                call: Call<List<UserAPI>>,
                response: Response<List<UserAPI>>
            ) {
                response.body()?.let { fetchedUsers(it) }
            }

            override fun onFailure(call: Call<List<UserAPI>>, t: Throwable) {
                Log.e("UserViewModel.kt", "onFailure")
            }

        })
    }

    fun setAvatar(avatar_uri: Uri) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val builder = Data.Builder()
        builder.putString("avatar_uri", avatar_uri.toString())

        val setAvatarRequest = OneTimeWorkRequestBuilder<UpdateProfilePhoto>()
            .setConstraints(constraints)
            .setInputData(builder.build())
            .build()

        workManager.beginUniqueWork("SET_AVATAR", ExistingWorkPolicy.REPLACE, setAvatarRequest)
            .enqueue()
    }

    fun removeAvatar() {
        sharedPreferences.remove("avatar")
    }
}
