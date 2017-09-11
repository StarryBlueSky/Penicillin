package jp.nephy.penicillin.request.handler

import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.Util
import jp.nephy.penicillin.request.credential.AccessToken
import jp.nephy.penicillin.request.credential.AccessTokenSecret
import jp.nephy.penicillin.request.credential.ConsumerKey
import jp.nephy.penicillin.request.credential.ConsumerSecret
import jp.nephy.penicillin.request.header.OAuthRequestHeader
import okhttp3.Request
import okhttp3.Response
import java.net.URL

class OAuthRequestHandler(private val ck: ConsumerKey, private val cs: ConsumerSecret, private val at: AccessToken, private val ats: AccessTokenSecret, uuid: String?=null, deviceId: String?=null): AbsRequestHandler() {
    private val uuid: String = uuid ?: Util.getRandomUUID()
    private val deviceId: String = deviceId ?: Util.getRandomUUID()

    fun send(method: HTTPMethod, url: URL, data: Map<String,String>?=null, file: ByteArray?=null, contentType: String?=null): Pair<Request, Response> {
        val header = OAuthRequestHeader(method, url, uuid, deviceId).authenticate(ck, cs, at, ats, data, file != null)

        return when (method) {
            HTTPMethod.GET -> httpGet(url, header, data)
            HTTPMethod.POST -> if (file != null && contentType != null) {
                httpPostAsMultiPart(url, header, file, contentType, data)
            } else if (contentType == "application/json") {
                httpPostAsJson(url, header, data)
            } else {
                httpPostAsForm(url, header, data)
            }
            HTTPMethod.DELETE -> httpDelete(url, header, data)
        }
    }
}
