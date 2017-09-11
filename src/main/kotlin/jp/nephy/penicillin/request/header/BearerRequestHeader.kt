package jp.nephy.penicillin.request.header

import jp.nephy.penicillin.request.Util
import jp.nephy.penicillin.request.credential.BearerToken
import java.net.URL

class BearerRequestHeader(url: URL): AbsRequestHeader() {
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

    fun authenticate(token: BearerToken): BearerRequestHeader {
        builder["Authorization"] = "Bearer $token"
        return this
    }
}
