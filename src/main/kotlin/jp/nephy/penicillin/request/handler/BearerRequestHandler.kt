package jp.nephy.penicillin.request.handler

import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.credential.BearerToken
import jp.nephy.penicillin.request.header.BearerRequestHeader
import okhttp3.Request
import okhttp3.Response
import java.net.URL

class BearerRequestHandler(private val token: BearerToken): AbsRequestHandler() {
    fun send(method: HTTPMethod, url: URL, data: Map<String,String>?=null): Pair<Request, Response> {
        val header = BearerRequestHeader(url).authenticate(token)

        return when (method) {
            HTTPMethod.GET -> httpGet(url, header, data)
            HTTPMethod.POST -> httpPostAsForm(url, header, data)
            HTTPMethod.DELETE -> httpDelete(url, header, data)
        }
    }
}