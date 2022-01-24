package net.teamof.whisper.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.api.*
import net.teamof.whisper.di.DataStoreManager
import net.teamof.whisper.models.OBKeyValue
import net.teamof.whisper.models.OBKeyValue_
import net.teamof.whisper.models.UserAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class UserViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val authAPI: AuthAPI,
    private val searchAPI: SearchAPI,
    private val accountAPI: AccountAPI
) :
    ViewModel() {

    private val oBKeyValueBox: Box<OBKeyValue> = ObjectBox.store.boxFor()

    fun getUserID(): Long {
        val userId = oBKeyValueBox.query().run {
            equal(OBKeyValue_.key, "user_id")
            build()
        }.use { it.findFirst() }

        return userId?.value?.toLong() ?: 0L
    }

    private suspend fun setUserID(user_id: Long) {
        dataStoreManager.setUserId(user_id)
    }

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
            val response = authAPI.signIn(
                LoginRequest(
                    username,
                    password
                )
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    buttonLoading(false)
                    buttonEnabled(false)
                    buttonText("Signing In...")
                    val jsonRes = JSONObject(response.body()?.string())

                    oBKeyValueBox.put(OBKeyValue(key = "token", value = jsonRes.getString("token")))
                    oBKeyValueBox.put(
                        OBKeyValue(
                            key = "user_id",
                            value = jsonRes.getString("user_id")
                        )
                    )

                    navController.navigate("Conversations") {
                        launchSingleTop = true
                        popUpTo("Login") { inclusive = true }
                    }
                } else {
                    buttonLoading(false)
                    buttonEnabled(false)
                    buttonText("Credentials Wrong")
                    buttonColor(0xFFe11d48)
                    Timer().schedule(2500) {
                        buttonColor(0xFF0336FF)
                        buttonText("Sign In")
                        buttonEnabled(true)
                    }
                }
            }
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
                        buttonText("Sign In")
                        buttonEnabled(true)
                    }
                }
            }
        }
    }

    suspend fun changeUsername(
        navController: NavController,
        newUsername: String,
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
                    newUsername
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
                        buttonText("Sign In")
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


    fun searchUsers(input: String, fetchedUsers: (List<UserAPI>) -> Unit) {

        val response = searchAPI.searchUsers(input)

        response.enqueue(object : Callback<List<UserAPI>> {
            override fun onResponse(
                call: Call<List<UserAPI>>,
                response: Response<List<UserAPI>>
            ) {
                response.body()?.let { fetchedUsers(it) }
                Timber.d(response.code().toString())
            }

            override fun onFailure(call: Call<List<UserAPI>>, t: Throwable) {
                Timber.d(t)
            }

        })
    }
}
