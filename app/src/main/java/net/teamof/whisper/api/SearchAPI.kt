package net.teamof.whisper.api

import com.squareup.moshi.JsonClass
import net.teamof.whisper.models.Contact
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

@JsonClass(generateAdapter = true)
data class SearchUsersRequest(
    val username: String
)

interface SearchAPI {
    @POST("search-users")
    @Headers("Content-Type: application/json")
    fun searchUsers(@Body searchUsersRequest: SearchUsersRequest): Call<List<Contact>>
}