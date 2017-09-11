package jp.nephy.penicillin.request.handler

import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.credential.ConsumerKey
import jp.nephy.penicillin.request.credential.ConsumerSecret
import jp.nephy.penicillin.request.header.BasicRequestHeader
import okhttp3.Request
import okhttp3.Response
import java.net.URL

class BasicRequestHandler(private val ck: ConsumerKey, private val cs: ConsumerSecret): AbsRequestHandler() {
    fun send(method: HTTPMethod, url: URL, data: Map<String,String>?=null): Pair<Request, Response> {
        val header = BasicRequestHeader(url).authenticate(ck, cs)

        return when (method) {
            HTTPMethod.GET -> httpGet(url, header, data)
            HTTPMethod.POST -> httpPostAsForm(url, header, data)
            HTTPMethod.DELETE -> httpDelete(url, header, data)
        }
    }
}
