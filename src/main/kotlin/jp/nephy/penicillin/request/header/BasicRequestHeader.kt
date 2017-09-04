package jp.nephy.penicillin.request.header

import jp.nephy.penicillin.request.Util
import jp.nephy.penicillin.request.credential.ConsumerKey
import jp.nephy.penicillin.request.credential.ConsumerSecret
import jp.nephy.penicillin.request.toBase64Encode
import java.net.URL

class BasicRequestHeader(url: URL): AbsRequestHeader() {
    init {
        _header.apply {
            put("Host", url.host)
            put("X-B3-TraceId", Util.getB3TraceId())
            put("X-Twitter-Client-Language", "ja")
            put("Accept", "*/*")
            put("Accept-Language", "ja")
            put("Authorization", "")
            put("Accept-Encoding", "gzip, deflate")
            put("User-Agent", "Twitter/7.5.1 CFNetwork/758.5.3 Darwin/15.6.0")
        }
    }

    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret): BasicRequestHeader {
        val encoded: String = "$ck:$cs".toBase64Encode()

        _header["Authorization"] = "Basic $encoded"
        return this
    }
}
