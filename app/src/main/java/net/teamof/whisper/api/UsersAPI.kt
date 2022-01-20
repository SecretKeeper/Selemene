package net.teamof.whisper.api

import net.teamof.whisper.models.UserAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UsersAPI {
    @GET("users/id/{id}")
    @Headers("Content-Type: application/json")
    fun getUserProfile(@Path("id") user_id: String): Call<UserAPI>
}