package net.teamof.whisper.api

import com.squareup.moshi.JsonClass
import net.teamof.whisper.models.UserAPI
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

@JsonClass(generateAdapter = true)
data class SearchUsersRequest(
    val username: String
)

interface SearchAPI {
    @POST("search/users")
    @Headers("Content-Type: application/json")
    fun searchUsers(@Query("username") username: String): Call<List<UserAPI>>
}