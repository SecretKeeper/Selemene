package net.teamof.whisper.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.auth0.android.jwt.JWT
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.api.AuthAPI
import net.teamof.whisper.api.LoginRequest
import net.teamof.whisper.api.SearchAPI
import net.teamof.whisper.api.SearchUsersRequest
import net.teamof.whisper.di.DataStoreManager
import net.teamof.whisper.models.Contact
import net.teamof.whisper.models.OBKeyValue
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
    private val searchAPI: SearchAPI
) :
    ViewModel() {

    private val OBKeyValueBox: Box<OBKeyValue> = ObjectBox.store.boxFor(OBKeyValue::class.java)

    fun getUserID(): LiveData<Long> {
        return dataStoreManager.getUserId().asLiveData()
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
                    val jwt = JWT(jsonRes.getString("token"))
                    Timber.d(jwt.getClaim("user_id").asString())
                    jwt.getClaim("user_id").asLong()?.let { setUserID(it) }
                    jwt.getClaim("user_id").asString()
                        ?.let { OBKeyValueBox.put(OBKeyValue(key = "user_id", value = it)) }

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

    fun searchUsers(input: String, fetchedUsers: (List<Contact>) -> Unit) {

        val response = searchAPI.searchUsers(SearchUsersRequest(input))

        response.enqueue(object : Callback<List<Contact>> {
            override fun onResponse(
                call: Call<List<Contact>>,
                response: Response<List<Contact>>
            ) {
                response.body()?.let { fetchedUsers(it) }
                Timber.d(response.body().toString())
            }

            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                Timber.d(t)
            }

        })
    }

}