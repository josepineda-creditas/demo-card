package com.example.card.listeners

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import kotlinx.coroutines.runBlocking
import com.example.card.handlers.PaymentRequestEventHandler
import com.example.card.listeners.aspects.CommonHandled
import com.example.card.models.event.PaymentRequestEvent
import com.example.card.service.PaymentRequestService
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class EventListener(
  private val log: Logger,
  private val paymentRequestEventHandler: PaymentRequestEventHandler,
  private val paymentRequestService: PaymentRequestService
) {
  @CommonHandled
  @SqsListener(
    "\${cloud.aws.sqs.paymentRequestQueueName}",
    deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS
  )
  fun paymentRequestListener(evt: PaymentRequestEvent) = runBlocking {
    paymentRequestEventHandler.process(
      paymentRequest = paymentRequestService.findById(evt.id)
    )
  }
}
