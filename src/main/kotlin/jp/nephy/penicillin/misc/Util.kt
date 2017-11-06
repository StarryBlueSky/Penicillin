package jp.nephy.penicillin.misc

import java.net.URLEncoder
import java.security.SecureRandom
import java.util.*

class Util {
    companion object {
        fun getCurrentEpochTime() = System.currentTimeMillis() / 1000

        fun getRandomUUID() = UUID.randomUUID().toString().toUpperCase()

        fun getB3TraceId(n: Int=16): String {
            val seed = SecureRandom()
            return StringBuilder().apply {
                for (i in 0 .. n) {
                    append(Integer.toHexString(seed.nextInt(16)))
                }
            }.toString()
        }
    }
}

internal fun String.toURLEncode(): String {
    val encoded = URLEncoder.encode(this, "UTF-8")
    return StringBuilder(encoded.length).apply {
        for (c in encoded) {
            append(when (c) {
                '+' -> "%20"
                '*' -> "%2A"
                else -> c
            })
        }
    }.toString()
}

internal fun String.unescapeHTMLCharacters() = this
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")

internal fun ByteArray.toBase64Encode() = Base64.getEncoder().encodeToString(this)
internal fun String.toBase64Encode() = this.toByteArray().toBase64Encode()
