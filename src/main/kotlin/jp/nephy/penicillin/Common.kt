package jp.nephy.penicillin

import java.net.URLEncoder
import java.security.MessageDigest
import java.util.*
import javax.xml.bind.DatatypeConverter

enum class HTTPMethod {
    GET, POST, DELETE
}

enum class AuthorizationType {
    OAuth, Basic, Bearer
}

class Common {
    companion object {
        fun getCurrentEpochTime(): Long {
            return System.currentTimeMillis() / 1000
        }

        fun getRandomUUID(): String {
            return UUID.randomUUID().toString().toUpperCase()
        }

        fun getB3TraceId(): String {
            return DatatypeConverter.printHexBinary(
                    MessageDigest
                            .getInstance("MD5")
                            .digest(getCurrentEpochTime().toString().toByteArray())
            ).slice(0..16)
        }
    }
}


open class CredentialsBase(key: String) {
    private var _key: String = key

    override fun toString(): String {
        return this._key
    }
}

class ConsumerKey(key: String): CredentialsBase(key)
class ConsumerSecret(secret: String): CredentialsBase(secret)
class AccessToken(token: String): CredentialsBase(token)
class AccessTokenSecret(secret: String): CredentialsBase(secret)
class BearerToken(token: String): CredentialsBase(token)


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

internal fun ByteArray.toBase64Encode(): String {
    return Base64.getEncoder().encodeToString(this)
}

internal fun String.toBase64Encode(): String {
    return this.toByteArray().toBase64Encode()
}

internal fun Map<String,String>.toParamString(): String {
    return this.map {
        "${it.key.toURLEncode()}=${it.value.toURLEncode()}"
    }.joinToString("&")
}

internal fun Map<String,String>.toParamList(): List<Pair<String,String>> {
    return this.toList().map {
        Pair(it.first.toURLEncode(), it.second.toURLEncode())
    }
}
