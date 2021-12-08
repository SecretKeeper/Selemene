package net.teamof.whisper.sockets

import net.teamof.whisper.repositories.KeyValueRepository
import okhttp3.Interceptor
import okhttp3.Response

class SocketUserTokenInterceptor constructor(
    private val keyValueRepository: KeyValueRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = keyValueRepository.getToken()

        val request = chain.request().newBuilder()
            .header("x-token", token)
            .build()
        return chain.proceed(request)
    }
}