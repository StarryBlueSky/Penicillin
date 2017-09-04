package jp.nephy.penicillin.request.handler

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import jp.nephy.penicillin.request.*
import jp.nephy.penicillin.request.credential.ConsumerKey
import jp.nephy.penicillin.request.credential.ConsumerSecret
import jp.nephy.penicillin.request.header.BasicRequestHeader
import java.net.URL

class BasicRequestHandler(private val ck: ConsumerKey, private val cs: ConsumerSecret): AbsRequestHandler() {
    fun send(method: HTTPMethod, url: URL, data: Map<String,String>?=null): Triple<Request, Response, Result<String, FuelError>> {
        val header: MutableMap<String,String> = BasicRequestHeader(url).authenticate(ck, cs).get()

        return when (method) {
            HTTPMethod.GET -> httpGet(url, header, data)
            HTTPMethod.POST -> httpPost(url, header, data)
            else -> throw NotImplementedError()
        }
    }
}
