package com.example.card.service

import com.example.card.models.PaymentRequest
import com.example.card.models.event.PaymentRequestEvent
import com.example.card.repository.PaymentRequestRepository
import com.example.card.support.exception.PaymentRequestNotFoundException
import org.springframework.stereotype.Service

@Service
class PaymentRequestService(
  private val paymentRequestRepository: PaymentRequestRepository,
  private val paymentRequestQueueService: PaymentRequestQueueService
) {

  suspend fun findById(id: String): PaymentRequest = paymentRequestRepository.findById(id).orElseThrow {
    PaymentRequestNotFoundException(id)
  }

  suspend fun createPaymentRequest(paymentRequest: PaymentRequest): PaymentRequest {
    var paymentRequestSaved = paymentRequestRepository.save(paymentRequest)

    paymentRequestQueueService.send(
      PaymentRequestEvent(
        id = paymentRequestSaved.id!!
      )
    )

    return paymentRequestSaved
  }
}
