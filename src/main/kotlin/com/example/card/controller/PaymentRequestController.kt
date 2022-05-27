package com.example.card.controller

import com.example.card.api.PaymentRequestApi
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import com.example.card.controller.dto.PaymentRequestDTO
import com.example.card.controller.dto.PaymentRequestIdDTO
import com.example.card.service.PaymentRequestService

@RestController
class PaymentRequestController(
  private val paymentRequestService: PaymentRequestService
) : PaymentRequestApi {
  override suspend fun createPaymentRequest(paymentRequest: PaymentRequestDTO) = ResponseEntity
    .status(HttpStatus.CREATED)
    .body(PaymentRequestIdDTO.fromEntity(paymentRequestService.createPaymentRequest(paymentRequest.toEntity())))
}
