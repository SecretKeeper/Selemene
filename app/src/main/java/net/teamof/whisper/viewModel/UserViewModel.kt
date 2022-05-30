package net.teamof.whisper.viewModel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavController
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
import net.teamof.whisper.sharedprefrences.SharedPreferencesManagerImpl
import net.teamof.whisper.ui.theme.AccentGreenLong
import net.teamof.whisper.workers.RevokeTokenWorker
import net.teamof.whisper.workers.UpdateProfilePhoto
import org.json.JSONObject
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

	fun getUserID(): Long = sharedPreferences.getLong("userId", 0L)

	fun getUsername(): String = sharedPreferences.getString("username", "")

	fun getEmail(): String = sharedPreferences.getString("email", "")

	fun getAvatar(): String = sharedPreferences.getString("avatar", "")

	fun getStatus(): String = sharedPreferences.getString("status", "")

	fun getDescription(): String = sharedPreferences.getString("description", "")

	suspend fun authenticate(
		navController: NavController,
		username: String,
		password: String,
		buttonLoading: (Boolean) -> Unit,
		buttonText: (String) -> Unit,
		buttonColor: (Long) -> Unit,
		buttonEnabled: (Boolean) -> Unit
	) {
		buttonLoading(true)
		buttonEnabled(false)

		CoroutineScope(Dispatchers.IO).launch {
			val response = authAPI.login(
				LoginRequest(
					username,
					password
				)
			)
			if (response.isSuccessful) {
				buttonLoading(false)
				buttonEnabled(false)
				buttonText("Logging In...")
				val jsonRes = JSONObject(response.body()?.string())
				val userRes = JSONObject(jsonRes.getString("user"))

				sharedPreferences.set("accessToken", jsonRes.getString("access_token"))
				sharedPreferences.set("refreshToke", jsonRes.getString("refresh_token"))
				sharedPreferences.set("accessTokenExpiresAt", jsonRes.getString("expires"))

				sharedPreferences.set("userId", userRes.getString("user_id").toLong())
				sharedPreferences.set("username", userRes.getString("username"))
				sharedPreferences.set("email", userRes.getString("email"))
				sharedPreferences.set("avatar", userRes.getString("avatar"))

				getLoggedUserProfile(userRes.getLong("user_id"))

				withContext(Dispatchers.Main) {
					navController.navigate("Conversations") {
						launchSingleTop = true
						popUpTo("Login") { inclusive = true }
					}
				}


			} else {
				buttonLoading(false)
				buttonEnabled(false)
				buttonText("Credentials Wrong")
				buttonColor(0xFFe11d48)
				Timer().schedule(2500) {
					buttonColor(0xFF0336FF)
					buttonText("Login")
					buttonEnabled(true)
				}

			}
		}
	}

	fun signOut(navController: NavController) {

		val constraints = Constraints.Builder()
			.setRequiredNetworkType(NetworkType.CONNECTED)
			.build()

		val builder = Data.Builder()
		builder.putString("refreshToken", sharedPreferences.getString("refreshToken", ""))

		val revokeTokenRequest = OneTimeWorkRequestBuilder<RevokeTokenWorker>()
			.setConstraints(constraints)
			.setInputData(builder.build())
			.build()

		workManager.beginUniqueWork("REVOKE_TOKEN", ExistingWorkPolicy.KEEP, revokeTokenRequest)
			.enqueue()

		sharedPreferences.clear()

		navController.navigate("Login") {
			launchSingleTop = true
			popUpTo(0)
		}
	}

	suspend fun changePassword(
		navController: NavController,
		current_password: String,
		new_password: String,
		buttonLoading: (Boolean) -> Unit,
		buttonText: (String) -> Unit,
		buttonColor: (Long) -> Unit,
		buttonEnabled: (Boolean) -> Unit
	) {
		buttonLoading(true)
		buttonEnabled(false)

		CoroutineScope(Dispatchers.IO).launch {
			val response = accountAPI.changePassword(
				ChangePasswordRequest(
					current_password,
					new_password
				)
			)
			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					buttonLoading(false)
					buttonEnabled(false)
					buttonText("Password Change Successfully")
					buttonColor(AccentGreenLong)

					Timer().schedule(2000) {
						buttonColor(0xFF0336FF)
						buttonText("Change Password")
						buttonEnabled(true)
					}
				} else {
					buttonLoading(false)
					buttonEnabled(false)
					buttonText("Credentials Wrong")
					buttonColor(0xFFe11d48)
					Timer().schedule(2500) {
						buttonColor(0xFF0336FF)
						buttonText("Change Password")
						buttonEnabled(true)
					}
				}
			}
		}
	}

	suspend fun changeUsername(
		navController: NavController,
		newUsername: String,
		current_password: String,
		buttonLoading: (Boolean) -> Unit,
		buttonText: (String) -> Unit,
		buttonColor: (Long) -> Unit,
		buttonEnabled: (Boolean) -> Unit
	) {
		buttonLoading(true)
		buttonEnabled(false)

		CoroutineScope(Dispatchers.IO).launch {
			val response = accountAPI.changeUsername(
				ChangeUsernameRequest(
					newUsername,
					current_password
				)
			)
			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					buttonLoading(false)
					buttonEnabled(false)
					buttonText("Username Changed")
					buttonColor(AccentGreenLong)

					if (response.code() == 200) sharedPreferences.set("username", newUsername)

					Timer().schedule(2000) {
						buttonColor(0xFF0336FF)
						buttonText("Change Username")
						buttonEnabled(true)
					}
				} else {
					buttonLoading(false)
					buttonEnabled(false)
					buttonText("Credentials Wrong")
					buttonColor(0xFFe11d48)
					Timer().schedule(2500) {
						buttonColor(0xFF0336FF)
						buttonText("Change Username")
						buttonEnabled(true)
					}
				}
			}
		}
	}

	suspend fun changeEmail(
		navController: NavController,
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
			val response = accountAPI.changeEmail(
				ChangeEmailRequest(
					email,
					password
				)
			)
			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					buttonLoading(false)
					buttonEnabled(false)
					buttonText("Done..")
//                    navController.navigate("Conversations") {
//                        launchSingleTop = true
//                        popUpTo("Login") { inclusive = true }
//                    }
				} else {
					buttonLoading(false)
					buttonEnabled(false)
					buttonText("Credentials Wrong")
					buttonColor(0xFFe11d48)
					Timer().schedule(2500) {
						buttonColor(0xFF0336FF)
						buttonText("Change Email")
						buttonEnabled(true)
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
		navController: NavController,
		newStatus: String,
		buttonLoading: (Boolean) -> Unit,
		buttonText: (String) -> Unit,
		buttonColor: (Long) -> Unit,
		buttonEnabled: (Boolean) -> Unit
	) {
		buttonLoading(true)
		buttonEnabled(false)

		CoroutineScope(Dispatchers.IO).launch {
			val response = profileAPI.setStatus(
				SetStatus(status = newStatus)
			)
			withContext(Dispatchers.Main) {
				if (response.isSuccessful) {
					buttonLoading(false)
					buttonEnabled(false)
					buttonText("Status Updated")
					buttonColor(AccentGreenLong)

					if (response.code() == 200) sharedPreferences.set("status", newStatus)

					Timer().schedule(2000) {
						buttonColor(0xFF0336FF)
						buttonText("Set Status")
						buttonEnabled(true)
					}
				} else {
					buttonLoading(false)
					buttonEnabled(false)
					buttonText("Credentials Wrong")
					buttonColor(0xFFe11d48)
					Timer().schedule(2500) {
						buttonColor(0xFF0336FF)
						buttonText("Set Status")
						buttonEnabled(true)
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
