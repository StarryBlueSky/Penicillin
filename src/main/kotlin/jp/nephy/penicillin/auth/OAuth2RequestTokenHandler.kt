package jp.nephy.penicillin.auth

import jp.nephy.penicillin.credential.ConsumerKey
import jp.nephy.penicillin.credential.ConsumerSecret
import jp.nephy.penicillin.misc.toBase64Encode
import jp.nephy.penicillin.misc.toURLEncode

class OAuth2RequestTokenHandler(private val ck: ConsumerKey, private val cs: ConsumerSecret) {
    fun sign(): String {
        val encoded: String = "${ck.toString().toURLEncode()}:${cs.toString().toURLEncode()}".toBase64Encode()

        return "Basic $encoded"
    }
}
