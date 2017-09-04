package jp.nephy.penicillin.request.header

import jp.nephy.penicillin.request.Util
import jp.nephy.penicillin.request.credential.BearerToken
import java.net.URL

class BearerRequestHeader(url: URL): AbsRequestHeader() {
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

    fun authenticate(token: BearerToken): BearerRequestHeader {
        _header["Authorization"] = "Bearer $token"
        return this
    }
}
