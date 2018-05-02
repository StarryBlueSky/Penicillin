package jp.nephy.penicillin

import mu.KotlinLogging
import java.net.URLEncoder
import java.util.*


class Util {
    companion object {
        val logger = KotlinLogging.logger("Penicillin")
    }
}

fun buildUrl(baseUrl: String, vararg queries: Pair<String, Any?>): String {
    return buildString {
        append(baseUrl)
        val queryString = queries.filter { it.second != null }.joinToString("&") { "${it.first.toURLEncode()}=${it.second.toString().toURLEncode()}" }
        if (queryString.isNotBlank()) {
            append("?$queryString")
        }
    }
}

internal fun Map<String, String>.toQueryString(): String {
    return map { "${it.key.toURLEncode()}=${it.value.toURLEncode()}" }.joinToString("&")
}

internal fun String.toURLEncode(): String {
    return buildString {
        for (c in URLEncoder.encode(this@toURLEncode, "UTF-8").replace("%7E", "~")) {
            append(when (c) {
                '+' -> "%20"
                '*' -> "%2A"
                else -> c
            })
        }
    }
}

internal fun String.unescapeHTMLCharacters(): String {
    return replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
}

internal fun ByteArray.toBase64Encode(): String {
    return Base64.getEncoder().encodeToString(this).orEmpty()
}
internal fun String.toBase64Encode(): String {
    return toByteArray().toBase64Encode()
}
