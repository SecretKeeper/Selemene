package net.teamof.whisper.api

import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

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

interface ProfileAPI {
	@GET("profiles/{id}")
	@Headers("Content-Type: application/json")
	suspend fun getProfileByID(@Path("id") id: Long): ProfileAPIResponse
}