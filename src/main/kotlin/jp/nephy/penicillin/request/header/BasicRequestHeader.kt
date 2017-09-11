package jp.nephy.penicillin.request.header

import jp.nephy.penicillin.request.Util
import jp.nephy.penicillin.request.credential.ConsumerKey
import jp.nephy.penicillin.request.credential.ConsumerSecret
import jp.nephy.penicillin.request.toBase64Encode
import java.net.URL

class BasicRequestHeader(url: URL): AbsRequestHeader() {
    init {
        builder.apply {
            add("Host", url.host)
            add("X-B3-TraceId", Util.getB3TraceId())
            add("X-Twitter-Client-Language", "ja")
            add("Accept", "*/*")
            add("Accept-Language", "ja")
            add("Authorization", "")
            add("Accept-Encoding", "gzip, deflate")
            add("User-Agent", "Twitter/7.5.1 CFNetwork/758.5.3 Darwin/15.6.0")
        }
    }

    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret): BasicRequestHeader {
        val encoded: String = "$ck:$cs".toBase64Encode()

        builder["Authorization"] = "Basic $encoded"
        return this
    }
}
