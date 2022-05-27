package com.example.card.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.example.card.support.Constants.Companion.CONNECT_TIMEOUT
import com.example.card.support.Constants.Companion.READ_TIMEOUT
import com.example.card.support.Constants.Companion.WRITE_TIMEOUT
import com.example.card.support.CustomBodyLoggerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class RetrofitConfig(
    private val log: Logger
) {

    @Value("\${platform.url}")
    private val platformUrl: String = ""

    @Bean
    fun basicLoggerInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return httpLoggingInterceptor
    }

    @Bean
    fun bodyLoggerInterceptor() = CustomBodyLoggerInterceptor(log)

    @Bean
    fun stdHttpClient(
        basicLoggerInterceptor: HttpLoggingInterceptor,
        bodyLoggerInterceptor: CustomBodyLoggerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(basicLoggerInterceptor)
            .addInterceptor(bodyLoggerInterceptor)
            .build()
    }

    @Bean
    fun authHttpClient(
        bodyLoggerInterceptor: CustomBodyLoggerInterceptor,
        basicLoggerInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(basicLoggerInterceptor)
        .addInterceptor(bodyLoggerInterceptor)
        .build()

    @Bean
    fun retrofitBuilder(mapper: ObjectMapper, authHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(platformUrl)
        .client(authHttpClient)
        .addConverterFactory(JacksonConverterFactory.create(mapper))

    @Bean
    fun retrofit(retrofitBuilder: Retrofit.Builder) = retrofitBuilder.build()
}
