package com.example.card.configuration

import com.amazonaws.auth.WebIdentityTokenCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.DefaultAwsRegionProviderChain
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.messaging.converter.MappingJackson2MessageConverter

@Configuration
class AwsSqsConfiguration(
  val log: Logger
) {

  @Value("\${cloud.aws.sqs.paymentRequestQueueName}")
  val defaultQueueName: String = ""

  @Bean
  fun mappingJackson2MessageConverter(objectMapper: ObjectMapper?): MappingJackson2MessageConverter? {
    val jackson2MessageConverter = MappingJackson2MessageConverter()
    jackson2MessageConverter.objectMapper = objectMapper!!
    return jackson2MessageConverter
  }

  @Bean
  fun queueMessagingTemplate(
    customAmazonSqs: AmazonSQSAsync
  ): QueueMessagingTemplate? {
    val template = QueueMessagingTemplate(customAmazonSqs)

    template.setDefaultDestinationName(defaultQueueName)

    return template
  }

  @Primary
  @Profile("dev", "staging", "prod")
  @Bean
  fun customAmazonSqs(): AmazonSQSAsync {
    log.info("Primary SqSConfiguration")
    val signingRegion = DefaultAwsRegionProviderChain().region
    log.info("Region: $signingRegion")
    val serviceEndpoint = "https://sqs.$signingRegion.amazonaws.com"

    return AmazonSQSAsyncClientBuilder.standard()
      .withEndpointConfiguration(
        AwsClientBuilder.EndpointConfiguration(serviceEndpoint, signingRegion)
      ).withCredentials(
        WebIdentityTokenCredentialsProvider.create()
      ).build()
  }
}
