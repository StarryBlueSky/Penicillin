package jp.nephy.penicillin.request.handler

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.credential.BearerToken
import jp.nephy.penicillin.request.header.BearerRequestHeader
import java.net.URL

class BearerRequestHandler(private val token: BearerToken): AbsRequestHandler() {
    fun send(method: HTTPMethod, url: URL, data: Map<String,String>?=null): Triple<Request, Response, Result<String, FuelError>> {
        val header: MutableMap<String,String> = BearerRequestHeader(url).authenticate(token).get()

        return when (method) {
            HTTPMethod.GET -> httpGet(url, header, data)
            HTTPMethod.POST -> httpPost(url, header, data)
            else -> throw NotImplementedError()
        }
    }
}
