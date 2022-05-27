package com.example.card.support

import okhttp3.Interceptor
import okhttp3.Response
import org.slf4j.Logger

class CustomBodyLoggerInterceptor(
    private val log: Logger
) : Interceptor {

    companion object {
        private const val MAX_BODY_SIZE = 1000000L
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        val body = response.body()
        if (body != null) {
            log.info(response.peekBody(MAX_BODY_SIZE).string())
        }

        return response
    }
}
