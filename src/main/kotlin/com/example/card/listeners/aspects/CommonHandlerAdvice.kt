package com.example.card.listeners.aspects

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Aspect
@Component
class CommonHandlerAdvice(
  private val log: Logger
) {

  @SuppressWarnings("TooGenericExceptionCaught")
  @Around("@annotation(CommonHandled)")
  fun commonHandler(invocation: ProceedingJoinPoint): Any? {
    return try {
      log.info("Starting")
      invocation.proceed()
    } catch (e: Exception) {
      if (e.cause == null) throw e
      extractInnerException(e)
    } finally {
      log.info("End")
    }
  }

  private fun extractInnerException(e: Exception) {
    when (val cause = e.cause) {
      else -> throw cause!!
    }
  }
}
