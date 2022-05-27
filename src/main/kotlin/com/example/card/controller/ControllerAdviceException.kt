package com.example.card.controller

import com.example.card.support.exception.ApiError
import com.example.card.support.exception.CustomErrorCodes
import com.example.card.support.exception.ValidationException
import com.example.card.support.exception.PaymentRequestNotFoundException
import org.slf4j.Logger
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException
import retrofit2.HttpException

@ControllerAdvice(annotations = [RestController::class])
class ControllerAdviceException(
  private val log: Logger
) {

  companion object {
    @JvmField
    val ERROR_REGEX = "(\\d+) (.+)".toRegex()
  }

  fun extractErrors(errorMessage: String?): Map<String, Any> {
    if (errorMessage == null) return emptyMap()

    val (code, message) = ERROR_REGEX.find(errorMessage)!!.destructured
    return mapOf(
      "code" to Integer.parseInt(code),
      "message" to message
    )
  }

  @ExceptionHandler(value = [(WebExchangeBindException::class)])
  fun handler(ex: WebExchangeBindException): ResponseEntity<Any>? {
    val errors = mutableMapOf<String, Any?>()
    ex.bindingResult.fieldErrors.forEach { errors[it.field] = extractErrors(it.defaultMessage) }

    val result = ApiError(
      message = "Validation error",
      code = CustomErrorCodes.VALIDATION_ERROR,
      fields = errors
    )

    log.warn("$result")

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result)
  }

  @ExceptionHandler(value = [(HttpException::class)])
  fun handler(ex: HttpException): ResponseEntity<Any>? {
    val code: Int = if (ex.code() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
      HttpStatus.FAILED_DEPENDENCY.value()
    } else ex.code()
    val result = ApiError(
      message = ex.message(),
      code = code,
      fields = ex.response()
    )

    log.warn("$result")

    return ResponseEntity.status(code).body(result)
  }

  @ExceptionHandler(value = [(ValidationException::class)])
  fun handler(ex: ValidationException): ResponseEntity<Any>? {
    val errors = mutableMapOf<String, Any?>()
    ex.errors.forEach { errors[it.propertyPath.toString()] = extractErrors(it.message) }

    val result = ApiError(
      message = "Validation error",
      code = CustomErrorCodes.VALIDATION_ERROR,
      fields = errors
    )

    log.warn("$result")

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result)
  }

  @ExceptionHandler(value = [(PaymentRequestNotFoundException::class)])
  fun handler(ex: PaymentRequestNotFoundException): ResponseEntity<Any>? {
    val result = ApiError(
      message = ex.message,
      code = CustomErrorCodes.NOT_FOUND
    )

    log.warn("$result")

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result)
  }

  @Primary
  @ExceptionHandler(value = [(ServerWebInputException::class)])
  fun handler(ex: ServerWebInputException): ResponseEntity<Any>? {
    log.warn("Bad Request: ${ex.message}")

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
  }
}
