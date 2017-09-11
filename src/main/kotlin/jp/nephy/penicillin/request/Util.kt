package jp.nephy.penicillin.request

import java.net.URLEncoder
import java.security.MessageDigest
import java.util.*
import javax.xml.bind.DatatypeConverter

class Util {
    companion object {
        fun getCurrentEpochTime() = System.currentTimeMillis() / 1000

        fun getRandomUUID() = UUID.randomUUID().toString().toUpperCase()

        fun getB3TraceId(): String {
            return DatatypeConverter.printHexBinary(
                    MessageDigest
                            .getInstance("MD5")
                            .digest(getCurrentEpochTime().toString().toByteArray())
            ).slice(0..16)
        }
    }
}

internal fun String.toURLEncode(): String {
    val encoded = URLEncoder.encode(this, "UTF-8")
    return StringBuilder(encoded.length).apply {
        for (c in encoded.toCharArray()) {
            append(when (c) {
                '+' -> "%20"
                '*' -> "%2A"
                else -> c
            })
        }
    }.toString()
}

internal fun ByteArray.toBase64Encode() = Base64.getEncoder().encodeToString(this)
internal fun String.toBase64Encode() = this.toByteArray().toBase64Encode()
