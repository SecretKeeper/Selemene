package net.teamof.whisper.api

import net.teamof.whisper.sharedprefrences.SharedPreferencesManagerImpl
import okhttp3.Interceptor
import okhttp3.Response

class UserTokenInterceptor constructor(
	private val sharedPreferences: SharedPreferencesManagerImpl
) : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {

		val token = sharedPreferences.getString("accessToken", "")

		val request = chain.request().newBuilder()
			.header("Authorization", "Bearer $token")
			.build()
		return chain.proceed(request)
	}
}