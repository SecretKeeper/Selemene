package net.teamof.whisper.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.models.*
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

	private val userBox: Box<User> = ObjectBox.store.boxFor()

	private val _userState: MutableLiveData<User> by lazy {
		MutableLiveData<User>(null)
	}

	val userState: MutableLiveData<User> = _userState

	fun setUserStateByUserID(user_id: Long, callback: () -> Unit) {
		val query = userBox.query().equal(User_.user_id, user_id)
		val user = query.build().findFirst()

		if (user == null) {
			fetchUserByID(user_id) { fetchedUser ->
				val newUser = User(
					user_id = fetchedUser.user_id,
					username = fetchedUser.username,
					email = fetchedUser.email,
					avatar = fetchedUser.avatar ?: "",
				)
				newUser.profile.target = Profile(
					status = fetchedUser.profile.status,
					description = fetchedUser.profile.description
				)
				newUser.counters.target = Counters(
					followers = fetchedUser._count?.followers ?: 0,
					feeds = fetchedUser._count?.feeds ?: 0
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

	fun getUserByUserID(user_id: Long): User? {
		var userResult: User? = null
		val query = userBox.query().equal(User_.user_id, user_id)
		val user = query.build().findFirst()

		if (user == null) {
			fetchUserByID(user_id) { fetchedUser ->
				val newUser = User(
					user_id = fetchedUser.user_id,
					username = fetchedUser.username,
					email = fetchedUser.email,
					avatar = fetchedUser.avatar ?: "",
				)
				newUser.profile.target = Profile(
					status = fetchedUser.profile.status,
					description = fetchedUser.profile.description
				)
				newUser.counters.target = Counters(
					followers = fetchedUser._count?.followers ?: 0,
					feeds = fetchedUser._count?.feeds ?: 0
				)

				userBox.put(newUser)
				userResult = newUser
			}

		} else userResult = user

		return userResult
	}

	private fun fetchUserByID(user_id: Long, function: (UserAPI) -> Unit): UserAPI? {
		val user: UserAPI? = null
		val response = usersAPI.getUserProfile(user_id.toString())

		response.enqueue(object : Callback<UserAPI> {
			override fun onResponse(
				call: Call<UserAPI>,
				response: Response<UserAPI>
			) {
				Timber.e(response.toString())
				response.body()?.let {
					function(it)
				}
			}

			override fun onFailure(call: Call<UserAPI>, t: Throwable) {
				Timber.e(t)
			}

		})

		return user
	}
}

