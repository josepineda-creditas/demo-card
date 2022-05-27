package com.example.card.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("PaymentRequest")
data class PaymentRequest(
  @Indexed
  val cardToken: String,
  val authorizationCode: Int,
  val expiredAt: Long,
  val amount: Double,
  val paymentMethod: PaymentMethod
) {
  @get:Id
  var id: String? = null
}

enum class PaymentMethod {
  DEBIT_CARD
}
