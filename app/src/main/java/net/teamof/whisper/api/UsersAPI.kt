package net.teamof.whisper.api

import com.squareup.moshi.JsonClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

@JsonClass(generateAdapter = true)
data class UserProfileResponse(
    val user_id: Long,
    val username: String,
    val email: String,
    val avatar: String,
)


interface UsersAPI {
    @GET("user/id/{id}")
    @Headers("Content-Type: application/json")
    fun getUserProfile(@Path("id") user_id: Long): Call<UserProfileResponse>
}