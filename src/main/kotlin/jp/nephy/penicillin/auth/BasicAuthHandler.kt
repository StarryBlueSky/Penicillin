package jp.nephy.penicillin.auth

import jp.nephy.penicillin.credential.ConsumerKey
import jp.nephy.penicillin.credential.ConsumerSecret
import jp.nephy.penicillin.misc.toBase64Encode

class BasicAuthHandler(private val ck: ConsumerKey, private val cs: ConsumerSecret) {
    fun sign(): String {
        val encoded: String = "$ck:$cs".toBase64Encode()

        return "Basic $encoded"
    }
}
