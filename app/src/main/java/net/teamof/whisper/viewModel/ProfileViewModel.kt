package net.teamof.whisper.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.data.*
import net.teamof.whisper.models.UserAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val usersAPI: UsersAPI,
	private val userRepository: UserRepository,
	private val profileRepository: ProfileRepository,
	private val userCountersRepository: UserCountersRepository
) :
	ViewModel() {

	private val _userState: MutableLiveData<UserWithProfileAndCounters> by lazy {
		MutableLiveData<UserWithProfileAndCounters>(null)
	}

	val userState: MutableLiveData<UserWithProfileAndCounters> = _userState

	fun getUserWithProfileByIdBeforeNavigate(userId: Long, callback: () -> Unit) {
		CoroutineScope(Dispatchers.IO).launch {
			val userWithProfile: UserWithProfileAndCounters? =
				userRepository.getUserWithProfileById(userId)

			if (userWithProfile == null) {
				fetchUserById(userId) { fetchedUser ->
					CoroutineScope(Dispatchers.IO).launch {

						val user = User(
							userId = fetchedUser.user_id,
							username = fetchedUser.username,
							email = fetchedUser.email,
							avatar = fetchedUser.avatar
						)

						val profile = Profile(
							userId = fetchedUser.user_id,
							status = fetchedUser.profile.status,
							description = fetchedUser.profile.description
						)

						val userCounters = UserCounters(
							userId = fetchedUser.user_id,
							feeds = fetchedUser.counters!!.feeds.toLong(),
							followers = fetchedUser.counters.followers.toLong()
						)

						val profileWithCounters = ProfileWithCounters(
							profile = profile,
							userCounters = userCounters
						)

						userRepository.upsert(user)
						profileRepository.upsert(profile)
						userCountersRepository.upsert(userCounters)

						withContext(Dispatchers.Main) {
							_userState.value =
								UserWithProfileAndCounters(
									user = user,
									profile = profileWithCounters
								)
							callback()
						}
					}
				}
			} else withContext(Dispatchers.Main) {
				Log.e("OOOO", userWithProfile.toString())
				_userState.value = userWithProfile
				callback()
			}
		}
	}

	private fun fetchUserById(userId: Long, function: (UserAPI) -> Unit): UserAPI? {
		val user: UserAPI? = null
		val response = usersAPI.getUserProfile(userId.toString())

		response.enqueue(object : Callback<UserAPI> {
			override fun onResponse(
				call: Call<UserAPI>,
				response: Response<UserAPI>
			) {
				response.body()?.let {
					function(it)
				}
			}

			override fun onFailure(call: Call<UserAPI>, t: Throwable) {
				Log.e("ProfileViewModel.kt", "OnFailure ")
			}

		})

		return user
	}
}

