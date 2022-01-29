package net.teamof.whisper.api

import com.squareup.moshi.JsonClass
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

@JsonClass(generateAdapter = true)
data class SignupResponse(
    val token: String
)

@JsonClass(generateAdapter = true)
data class SignupRequest(
    val username: String,
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val token: String
)

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val username: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class SigninViaRefreshTokenRequest(
    val token: String
)

interface AuthAPI {
    @POST("auth/signup")
    @Headers("Content-Type: application/json")
    suspend fun signup(@Body loginRequest: SignupRequest): Response<ResponseBody>

    @POST("auth/signin")
    @Headers("Content-Type: application/json")
    suspend fun signIn(@Body loginRequest: LoginRequest): Response<ResponseBody>

    @POST("auth/signin-via-refresh-token")
    @Headers("Content-Type: application/json")
    suspend fun signinViaRefreshToken(@Header("Authorization") revokeTokenRequest: SigninViaRefreshTokenRequest): Response<ResponseBody>
}