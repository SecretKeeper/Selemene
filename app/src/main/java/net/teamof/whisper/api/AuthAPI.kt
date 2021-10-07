package net.teamof.whisper.api

import com.squareup.moshi.JsonClass
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val token: String
)

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val username: String,
    val password: String
)

interface AuthAPI {
    @POST("signup")
    @Headers("Content-Type: application/json")
    suspend fun signup(@Body loginRequest: LoginRequest): LoginResponse

    @POST("signin")
    @Headers("Content-Type: application/json")
    suspend fun signIn(@Body loginRequest: LoginRequest): Response<ResponseBody>
}