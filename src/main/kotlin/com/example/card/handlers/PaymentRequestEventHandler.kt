package com.example.card.handlers

import com.example.card.models.Payment
import com.example.card.models.PaymentRequest
import com.example.card.models.PaymentStatus
import com.example.card.service.PaymentService
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class PaymentRequestEventHandler(
  private val paymentService: PaymentService
) {
  suspend fun process(paymentRequest: PaymentRequest) {

    val isUnusualOperationResult = isUnusualOperation(paymentRequest)

    val payment=Payment(
      id=null,
      cardToken = paymentRequest.cardToken,
      paymentMethod = paymentRequest.paymentMethod,
      amount = paymentRequest.amount,
      paymentReference = "test",
      status = getStatusByOperationType(isUnusualOperationResult)
    )

    paymentService.createPayment(payment)
  }

  private fun isUnusualOperation(paymentRequest: PaymentRequest): Boolean = Random.nextBoolean()

  private fun getStatusByOperationType(isUnusualOperation:Boolean): PaymentStatus {
    return when(isUnusualOperation){
      true -> PaymentStatus.UNUSUAL_OPERATION
      else -> PaymentStatus.APPLIED
    }
  }
}
