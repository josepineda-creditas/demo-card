package com.example.card.support

import org.springframework.stereotype.Component

@Component
class Constants private constructor() {
    companion object {

        // Retrofit config constants
        const val CONNECT_TIMEOUT = 30L
        const val WRITE_TIMEOUT = 30L
        const val READ_TIMEOUT = 30L
    }

}
