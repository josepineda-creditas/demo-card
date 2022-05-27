package com.example.card.configuration

import com.example.card.gateway.PaymentGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit

@Configuration
class GatewaysConfig {

    @Bean
    fun personGateway(retrofit: Retrofit) = retrofit.create(PaymentGateway::class.java)
}
