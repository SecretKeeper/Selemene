package net.teamof.whisper.api

import net.teamof.whisper.repositories.KeyValueRepository
import okhttp3.Interceptor
import okhttp3.Response

class UserTokenInterceptor constructor(
    private val keyValueRepository: KeyValueRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = keyValueRepository.getToken()

        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}