package com.example.card.repository

import com.example.card.models.PaymentRequest
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRequestRepository : CrudRepository<PaymentRequest, String>
