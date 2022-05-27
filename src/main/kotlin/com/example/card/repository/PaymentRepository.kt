package com.example.card.repository

import com.example.card.models.Payment
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PaymentRepository : ReactiveCrudRepository<Payment, UUID>{
    suspend fun findAllWhereLimit10 (): List<Payment>
}
