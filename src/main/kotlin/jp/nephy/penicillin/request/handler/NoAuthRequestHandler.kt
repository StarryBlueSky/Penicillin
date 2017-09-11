package jp.nephy.penicillin.request.handler

import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.Util
import jp.nephy.penicillin.request.header.OAuthRequestHeader
import okhttp3.Request
import okhttp3.Response
import java.net.URL

class NoAuthRequestHandler(uuid: String?=null, deviceId: String?=null): AbsRequestHandler() {
    private val uuid: String = uuid ?: Util.getRandomUUID()
    private val deviceId: String = deviceId ?: Util.getRandomUUID()

    fun send(method: HTTPMethod, url: URL, data: Map<String,String>?=null): Pair<Request, Response> {
        val header = OAuthRequestHeader(method, url, uuid, deviceId)

        return when (method) {
            HTTPMethod.GET -> httpGet(url, header, data)
            HTTPMethod.POST -> httpPostAsForm(url, header, data)
            HTTPMethod.DELETE -> httpDelete(url, header, data)
        }
    }
}