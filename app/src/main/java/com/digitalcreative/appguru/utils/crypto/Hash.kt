package com.digitalcreative.appguru.utils.crypto

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object Hash {
    fun sha256(plain: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(plain.toByteArray(StandardCharsets.UTF_8))
            val hexStrings = StringBuffer()
            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())
                if (hex.length == 1) hexStrings.append('0')
                hexStrings.append(hex)
            }
            hexStrings.toString()
        } catch (e: RuntimeException) {
            throw RuntimeException(e)
        }
    }
}