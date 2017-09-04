package jp.nephy.penicillin.request.handler

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.Util
import jp.nephy.penicillin.request.credential.*
import jp.nephy.penicillin.request.header.OAuthRequestHeader
import java.net.URL

class OAuthRequestHandler(private val ck: ConsumerKey, private val cs: ConsumerSecret, private val at: AccessToken, private val ats: AccessTokenSecret, uuid: String?=null, deviceId: String?=null): AbsRequestHandler() {
    private val uuid: String = uuid ?: Util.getRandomUUID()
    private val deviceId: String = deviceId ?: Util.getRandomUUID()

    fun send(method: HTTPMethod, url: URL, data: Map<String,String>?=null): Triple<Request, Response, Result<String, FuelError>> {
        val header: MutableMap<String,String> = OAuthRequestHeader(method, url, uuid, deviceId).authenticate(ck, cs, at, ats, data).apply {
            if (method == HTTPMethod.POST) {
                setLength(data)
            }
        }.get()

        return when (method) {
            HTTPMethod.GET -> httpGet(url, header, data)
            HTTPMethod.POST -> httpPost(url, header, data)
            HTTPMethod.DELETE -> httpDelete(url, header, data)
        }
    }
}
