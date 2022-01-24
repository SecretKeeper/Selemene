package net.teamof.whisper.api

import com.squareup.moshi.JsonClass
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

@JsonClass(generateAdapter = true)
data class ChangeUsernameResponse(
    val message: String
)

@JsonClass(generateAdapter = true)
data class ChangeUsernameRequest(
    val username: String,
)


@JsonClass(generateAdapter = true)
data class ChangeEmailResponse(
    val message: String
)

@JsonClass(generateAdapter = true)
data class ChangeEmailRequest(
    val email: String,
    val password: String
)


//@JsonClass(generateAdapter = true)
//data class ChangePasswordResponse(
//    val message: String
//)

@JsonClass(generateAdapter = true)
data class ChangePasswordRequest(
    val current_password: String,
    val new_password: String
)


interface AccountAPI {
    @POST("user/change-username")
    @Headers("Content-Type: application/json")
    suspend fun changeUsername(@Body username: ChangeUsernameRequest): Response<ResponseBody>

    @POST("user/change-email")
    @Headers("Content-Type: application/json")
    suspend fun changeEmail(@Body changeEmailRequest: ChangeEmailRequest): Response<ResponseBody>

    @POST("user/change-password")
    @Headers("Content-Type: application/json")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<ResponseBody>
}