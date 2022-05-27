package com.example.card.gateway

import com.fasterxml.jackson.databind.JsonNode
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PaymentGateway {


    @POST("dummy/api/v1/payments")
    suspend fun createPayment(
        @Body data: JsonNode,
        @Header("Accept") accept: String = "application/json"
    ): JsonNode
}
