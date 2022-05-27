package com.example.card.support.exception

import com.example.card.controller.dto.PaymentRequestDTO
import javax.validation.ConstraintViolation

class ValidationException(val errors: MutableIterator<ConstraintViolation<PaymentRequestDTO>>) : Exception()
