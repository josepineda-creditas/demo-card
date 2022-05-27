package com.example.card.support.exception

class PaymentRequestNotFoundException(id: String?) : Exception(
  "Payment request $id was not found"
)
