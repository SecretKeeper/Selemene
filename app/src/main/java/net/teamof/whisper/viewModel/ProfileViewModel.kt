package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.api.User
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.models.Counters
import net.teamof.whisper.models.Profile
import net.teamof.whisper.models.User_
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val usersAPI: UsersAPI
) :
    ViewModel() {

    private val userBox: Box<net.teamof.whisper.models.User> = ObjectBox.store.boxFor()

    private val _userState: MutableLiveData<net.teamof.whisper.models.User> by lazy {
        MutableLiveData<net.teamof.whisper.models.User>(null)
    }

    val userState: MutableLiveData<net.teamof.whisper.models.User> = _userState

    fun getUserByUserID(user_id: Long, callback: () -> Unit) {
        val query = userBox.query().equal(User_.user_id, user_id)
        val user = query.build().findFirst()

        if (user == null) {
            fetchUserByID(user_id) { fetchedUser ->
                val newUser = net.teamof.whisper.models.User(
                    user_id = fetchedUser.user_id,
                    username = fetchedUser.username,
                    email = fetchedUser.email,
                    avatar = fetchedUser.avatar,
                )
                newUser.profile.target = Profile(description = fetchedUser.profile.description)
                newUser.counters.target = Counters(
                    followers = fetchedUser._count.followers,
                    feeds = fetchedUser._count.feeds
                )

                userBox.put(newUser)
                userState.value = newUser
                callback()
            }

        } else {
            _userState.value = user
            callback()
        }
    }

    private fun fetchUserByID(user_id: Long, function: (User) -> Unit): User? {
        val user: User? = null
        val response = usersAPI.getUserProfile(user_id)

        response.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                Timber.e(response.toString())
                response.body()?.let {
                    function(it)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Timber.e(t)
            }

        })

        return user
    }
}

