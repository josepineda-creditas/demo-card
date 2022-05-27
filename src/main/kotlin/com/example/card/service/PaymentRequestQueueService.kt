package com.example.card.service

import com.example.card.models.event.PaymentRequestEvent
import com.example.card.support.SignatureGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class PaymentRequestQueueService(
  private val messagingTemplate: QueueMessagingTemplate,
  private val log: Logger,
  private val signer: SignatureGenerator,
  private val mapper: ObjectMapper
) {
  companion object {
    private const val deduplicationKey = "42831dbf-ba7e-4b3e-914e-eec53fc3d984"
  }

  @Value("\${cloud.aws.sqs.paymentRequestQueueName}")
  val fifoQueue: String = ""

  fun send(evt: PaymentRequestEvent) {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
      val hash = signer.generate(mapper.writeValueAsString(evt), deduplicationKey)

      messagingTemplate.convertAndSend(
        fifoQueue,
        evt,
        mapOf(
          "message-group-id" to evt.id,
          "message-deduplication-id" to hash
        )
      )

      log.info("Sent evt: id: ${evt.id}")
    }
  }
}
