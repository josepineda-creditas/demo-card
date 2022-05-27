package com.example.card.api

import com.example.card.controller.dto.PaymentRequestDTO
import com.example.card.controller.dto.PaymentRequestIdDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@RequestMapping("/v1/payments")
interface PaymentRequestApi {

  @PostMapping
  suspend fun createPaymentRequest(
    @Valid @RequestBody paymentRequest: PaymentRequestDTO
  ): ResponseEntity<PaymentRequestIdDTO>

}
