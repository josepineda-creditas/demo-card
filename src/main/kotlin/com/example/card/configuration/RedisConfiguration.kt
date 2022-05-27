package com.example.card.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import java.net.URI

@Configuration
class RedisConfiguration {

  @Value("\${redis.url}")
  private val redisUrl: String? = null

  @Bean
  fun jedisConnectionFactory(): JedisConnectionFactory? {
    if (redisUrl != null) {
      val info = redisConnectionInfo(redisUrl)
      val factory = JedisConnectionFactory()
      factory.hostName = info.hostname
      factory.port = info.port
      return factory
    }
    return JedisConnectionFactory()
  }

  private fun redisConnectionInfo(connUri: String): RedsConnectionInfo {
    val uri: URI = URI.create(connUri)
    val connInfo = RedsConnectionInfo()
    connInfo.hostname = uri.host
    connInfo.port = if (uri.port == -1) connInfo.port else uri.port
    return connInfo
  }

  @Bean
  fun redis(): RedisTemplate<String, Any>? {
    val template = RedisTemplate<String, Any>()
    template.setConnectionFactory(jedisConnectionFactory()!!)
    return template
  }
}

class RedsConnectionInfo(
  var hostname: String = "localhost",
  var port: Int = 6379
)
