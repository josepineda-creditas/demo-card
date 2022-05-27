package com.example.card.models

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("payments")
data class Payment(
    @Id
    @JvmField var id: UUID?,
    val cardToken: String? = null,
    val paymentMethod: PaymentMethod,
    val amount: Double,
    val paymentReference: String,
    val status: PaymentStatus? = null
) : Persistable<UUID> {

    override fun isNew(): Boolean {
        val isNewPayment: Boolean = id == null
        if (isNewPayment) id = UUID.randomUUID()
        return isNewPayment
    }

    override fun getId(): UUID? = id
}

enum class PaymentStatus{
    APPLIED,
    CANCELLED,
    UNUSUAL_OPERATION
}