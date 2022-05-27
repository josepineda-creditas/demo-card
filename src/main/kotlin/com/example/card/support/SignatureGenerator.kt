package com.example.card.support

import org.springframework.stereotype.Component
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Component
class SignatureGenerator {

  fun generate(payload: String, key: String): String {
    val hMacSHA256 = Mac.getInstance("HmacSHA256")
    val secretKey = SecretKeySpec(key.encodeToByteArray(), "HmacSHA256")
    hMacSHA256.init(secretKey)
    val data = hMacSHA256.doFinal(payload.encodeToByteArray())
    val builder = StringBuilder()
    data.forEach { builder.append(String.format("%02x", it)) }
    return builder.toString()
  }
}
