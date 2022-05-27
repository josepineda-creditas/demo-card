package com.example.card.controller.dto

import com.example.card.models.PaymentMethod
import com.example.card.models.PaymentRequest

data class PaymentRequestDTO(
  var cardToken: String,
  val authorizationCode: Int,
  val expiredAt: Long,
  val amount: Double,
  val paymentMethod: PaymentMethod
) {
  fun toEntity() = PaymentRequest(
    cardToken = cardToken,
    authorizationCode = authorizationCode,
    expiredAt = expiredAt,
    amount = amount,
    paymentMethod = paymentMethod
  )

  companion object {
    fun fromEntity(paymentRequest: PaymentRequest) = PaymentRequestDTO(
      cardToken = paymentRequest.cardToken,
      authorizationCode = paymentRequest.authorizationCode,
      expiredAt = paymentRequest.expiredAt,
      amount = paymentRequest.amount,
      paymentMethod = paymentRequest.paymentMethod
    )
  }
}

class PaymentRequestIdDTO(
  val id: String
) {
  companion object {
    fun fromEntity(paymentRequest: PaymentRequest) = PaymentRequestIdDTO(
      id = paymentRequest.id!!
    )
  }
}
