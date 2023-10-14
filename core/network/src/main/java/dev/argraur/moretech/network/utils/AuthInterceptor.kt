package dev.argraur.moretech.network.utils

import dev.argraur.moretech.network.token.TokenProvider
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.getToken() ?: throw IllegalAccessException()
        return chain.proceed(authenticateRequest(chain.request(), token))
    }

    private fun authenticateRequest(request: Request, token: String): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
}