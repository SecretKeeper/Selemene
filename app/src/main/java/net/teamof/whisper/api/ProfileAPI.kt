package net.teamof.whisper.api

import com.squareup.moshi.JsonClass
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

@JsonClass(generateAdapter = true)
data class ProfileAPIResponse(
	val id: Long,
	val user_id: Long,
	val status: String,
	val description: String,
	val created_at: String,
	val updated_at: String?,
	val deleted_at: String?,
)

@JsonClass(generateAdapter = true)
data class SetStatus(
	val sender: Long = 0,
	val status: String
)

interface ProfileAPI {
	@GET("profiles/{id}")
	@Headers("Content-Type: application/json")
	suspend fun getProfileByID(@Path("id") id: Long): ProfileAPIResponse

	@POST("profile/set-status")
	@Headers("Content-Type: application/json")
	suspend fun setStatus(@Body setStatus: SetStatus): Response<ResponseBody>
}