package jp.nephy.penicillin.request

import jp.nephy.jsonkt.JsonKt
import jp.nephy.penicillin.*
import jp.nephy.penicillin.endpoint.PrivateEndpoint
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody


val deviceUUID = OAuthSigner.getRandomUUID()
val clientUUID = OAuthSigner.getRandomUUID()
private val officialClientHeaders = arrayOf(
        "User-Agent" to "Twitter-iPhone/7.21.2 iOS/11.0.2 (Apple;iPhone10,1;;;;;1;2017)",
        "X-Twitter-Client" to "Twitter-iPhone",
        //"Geolocation" to "",
        "X-Twitter-Client-DeviceID" to deviceUUID,
        "X-Twitter-Active-User" to "yes",
        "kdt" to null,
        "X-Twitter-Client-Version" to "7.21.2",
        "X-Twitter-Client-Limit-Ad-Tracking" to "1",
        "X-Twitter-Polling" to "false",
        "X-B3-TraceId" to null,
        "Authorization" to null,
        "Accept-Language" to "ja",
        "Timezone" to "Asia/Tokyo",
        "X-Twitter-Client-Language" to "ja",
        "X-Client-UUID" to clientUUID,
        "X-Twitter-API-Version" to "5"
)

enum class HTTPMethod {
    GET, POST, DELETE
}

class PenicillinRequestBuilder(val session: Session, val httpMethod: HTTPMethod, url: String, val authorizationType: AuthorizationType) {
    private val okhttpRequestBuilder = Request.Builder()
    val url = if (url.startsWith("/")) {
        "https://api.twitter.com/1.1$url"
    } else {
        url
    }

    private val headers = linkedMapOf<String, String>()
    val queries = linkedMapOf<String, String>()
    val data = linkedMapOf<String, String>()
    private var fileData: RequestBody? = null
    private var isFormData: Boolean = false
    private var isJsonData: Boolean = false
    var isFileData: Boolean = false

    var callbackUrl: String? = null

    fun callback(value: String?) {
        callbackUrl = value
    }

    fun header(vararg header: Pair<String, Any?>?) {
        header.forEach {
            if (it?.second != null) {
                headers[it.first] = it.second.toString()
            }
        }
    }

    fun query(vararg query: Pair<String, Any?>?, onlyOfficialClient: Boolean = false) {
        if (onlyOfficialClient && authorizationType == AuthorizationType.OAuth1a && ! session.isOfficialClient) {
            return
        }

        query.forEach {
            if (it?.second != null) {
                queries[it.first] = it.second.toString()
            }
        }
    }

    fun form(vararg form: Pair<String, Any?>?, onlyOfficialClient: Boolean = false) {
        if (onlyOfficialClient && authorizationType == AuthorizationType.OAuth1a && ! session.isOfficialClient) {
            return
        }

        isFormData = true
        form.forEach {
            if (it?.second != null) {
                data[it.first] = it.second.toString()
            }
        }
    }

    fun json(vararg jsons: Pair<String, String>?) {
        isJsonData = true
        jsons.forEach {
            if (it?.second != null) {
                data[it.first] = it.second
            }
        }
    }

    fun file(file: ByteArray, contentType: String, name: String) {
        isFileData = true
        fileData = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .apply {
                    data.forEach {
                        addFormDataPart(it.key, it.value.toURLEncode())
                    }
                }
                .addFormDataPart(name, "blob", RequestBody.create(MediaType.parse(contentType), file))
                .build()
    }

    fun build(): Request {
        val trace = Thread.currentThread().stackTrace.find { it.className.startsWith("jp.nephy.penicillin.endpoint") }
        if (trace != null) {
            val method = javaClass.classLoader.loadClass(trace.className).methods.find { it.name == trace.methodName }
            if (method != null) {
                val annotation = method.getAnnotation(PrivateEndpoint::class.java)
                if (annotation != null && ! session.isOfficialClient) {
                    throw PenicillinLocalizedException(LocalizedString.PrivateEndpointRequiresOfficialClientCredentials)
                }
            }
        }

        if (authorizationType == AuthorizationType.OAuth1a && ! session.isOfficialClient) {
            header(*officialClientHeaders, "kdt" to session.knownDeviceToken, "X-B3-TraceId" to OAuthSigner.getB3TraceId())
        }
        header("Authorization" to OAuthSigner(session, this).sign())

        val request = when (httpMethod) {
            HTTPMethod.GET -> okhttpRequestBuilder.get()
            HTTPMethod.POST -> {
                val body = when {
                    isFileData -> fileData!!
                    isFormData -> RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), data.toQueryString())
                    isJsonData -> RequestBody.create(MediaType.parse("application/json"), JsonKt.toJsonString(data))
                    else -> RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), "")
                }
                okhttpRequestBuilder.post(body)
            }
            HTTPMethod.DELETE -> okhttpRequestBuilder.delete()
        }.url(buildUrl(url, *queries.toList().toTypedArray())
        ).apply {
            headers.forEach { header(it.key, it.value) }
        }

        return request.build()
    }
}
