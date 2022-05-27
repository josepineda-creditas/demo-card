package com.example.card.service


import com.example.card.models.Payment
import com.example.card.repository.PaymentRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository
) {
    suspend fun createPayment(payment: Payment): Payment = paymentRepository.save(payment).awaitFirst()

    suspend fun findAll():List<Payment> = paymentRepository.findAllWhereLimit10()
}
