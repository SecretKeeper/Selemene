package net.teamof.whisper.api

import com.squareup.moshi.JsonClass
import net.teamof.whisper.models.UserAPI
import net.teamof.whisper.models.UserAPIWithoutCounters
import retrofit2.Call
import retrofit2.http.*

@JsonClass(generateAdapter = true)
data class GetMultipleUsers(
	val ids: List<Long>
)

interface UsersAPI {
	@GET("users/id/{id}")
	@Headers("Content-Type: application/json")
	fun getUserProfile(@Path("id") user_id: String): Call<UserAPI>

	@POST("users/multiple")
	@Headers("Content-Type: application/json")
	fun getMultipleUsers(@Body ids: GetMultipleUsers): Call<List<UserAPIWithoutCounters>>
}