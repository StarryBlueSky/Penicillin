package jp.nephy.penicillin

import com.google.gson.Gson
import jp.nephy.penicillin.annotation.MustBeCalled
import jp.nephy.penicillin.auth.AuthorizationType
import jp.nephy.penicillin.exception.TwitterAPIError
import jp.nephy.penicillin.misc.HTTPMethod
import jp.nephy.penicillin.misc.Util
import jp.nephy.penicillin.misc.toURLEncode
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import java.net.ConnectException

class PenicillinRequest(private val session: Session) {
    private val builder = Request.Builder()
    private var method: HTTPMethod? = null
    private var url: String? = null
    private var originalUrl: String? = null
    private var params = linkedMapOf<String, String>()
    private var data = linkedMapOf<String, String>()
    private var body: RequestBody? = null
    private var hasFile: Boolean = false
    private var isFormData: Boolean = false
    private var isJsonData: Boolean = false
    private var callbackUrl: String? = null

    private val defaultHeaders = listOf(
        "kdt" to "", // TODO: Generate known device token(kdt)
        "X-Twitter-Client-DeviceID" to "00000000-0000-0000-0000-000000000000",
        "X-Twitter-Client-Version" to "7.10",
        "Accept" to "*/*",
        "X-Client-UUID" to Util.getRandomUUID(),
        "X-Twitter-Client-Language" to "ja",
        "X-B3-TraceId" to Util.getB3TraceId(),
        "Accept-Language" to "ja",
        "X-Twitter-Client" to "Twitter-iPhone",
        "User-Agent" to "Twitter-iPhone/7.10 iOS/10.2 (Apple;iPhone8,2;;;;;1)",
        "X-Twitter-Client-Limit-Ad-Tracking" to "1",
        "X-Twitter-API-Version" to "5",
        "X-Twitter-UTCOffset" to "+0900",
        "X-Twitter-Active-User" to "yes",
        "X-Twitter-Polling" to "true"
    )

    private var authorizationType = session.authType
    fun type(type: AuthorizationType) = this.apply {
        authorizationType = type
    }

    @MustBeCalled
    fun url(url: String) = this.apply {
        this.url = if (url.startsWith("/")) {
            "https://api.twitter.com/1.1$url"
        } else {
            url
        }
        originalUrl = this.url
    }

    fun callback(url: String?) = this.apply {
        callbackUrl = url
    }

    fun header(header: Pair<String, String>?) = this.apply {
        if (header != null) {
            builder.header(header.first, header.second)
        }
    }
    fun headers(headers: List<Pair<String, String>>?) = this.apply {
        headers?.forEach {
            header(it)
        }
    }

    private fun expandParameters(params: Map<String, String>): String {
        return params.map { "${it.key.toURLEncode()}=${it.value.toURLEncode()}" }.joinToString("&")
    }

    fun param(param: Pair<String, Any?>?) = this.apply {
        if (param?.second != null) {
            params[param.first] = param.second.toString()
        }
    }

    fun params(vararg params: Pair<String, Any?>?) = this.apply {
        params.forEach {
            param(it)
        }
    }

    fun paramIfOfficial(param: Pair<String, Any?>?) = this.apply {
        if (session.useOfficialKeys) {
            this@PenicillinRequest.param(param)
        }
    }

    fun dataAsForm(vararg forms: Pair<String, Any?>?) = this.apply {
        isFormData = true
        forms.forEach {
            if (it?.second != null) {
                data[it.first] = it.second.toString()
            }
        }
    }

    fun dataAsFormIfOfficial(vararg data: Pair<String, Any?>?) = this.apply {
        if (session.useOfficialKeys) {
            dataAsForm(*data)
        }
    }

    fun dataAsJson(vararg jsons: Pair<String, String>?) = this.apply {
        isJsonData = true
        jsons.forEach {
            if (it?.second != null) {
                data[it.first] = it.second
            }
        }
    }

    fun file(file: ByteArray, contentType: String, name: String="media") = this.apply {
        hasFile = true
        body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .apply {
                    data.forEach {
                        addFormDataPart(it.key, it.value.toURLEncode())
                    }
                }
                .addFormDataPart(name, "blob", RequestBody.create(MediaType.parse(contentType), file))
                .build()
    }

    fun get() = this.apply {
        method = HTTPMethod.GET
        builder.get()
    }.execute()

    fun post() = this.apply {
        method = HTTPMethod.POST

        if (isFormData) {
            body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), expandParameters(data))
        } else if (isJsonData) {
            body = RequestBody.create(MediaType.parse("application/json"), Gson().toJson(data))
        }
        if (body == null) {
            body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), "")
        }

        builder.post(body!!)
    }.execute()

    fun delete() = this.apply {
        method = HTTPMethod.DELETE
        builder.delete()
    }.execute()

    private fun execute(): PenicillinResponse {
        var requestUrl = url
        if (params.isNotEmpty()) {
            requestUrl += "?${expandParameters(params)}"
        }
        if (requestUrl == null) {
            throw IllegalStateException("url must be non-null.")
        }

        if (session.useOfficialKeys) {
            headers(defaultHeaders)
        }

        val sign = when (authorizationType) {
            AuthorizationType.OAuth1a -> session.oauth!!.sign(method!!, originalUrl!!, (params + data).toList(), hasFile)
            AuthorizationType.OAuth1aRequestToken -> session.oauthrt!!.sign(method!!, originalUrl!!, (params + data).toList(), callbackUrl)
            AuthorizationType.OAuth2 -> session.oauth2!!.sign()
            AuthorizationType.OAuth2RequestToken -> session.oauth2rt!!.sign()
            else -> null
        }
        if (sign != null) {
            header(Pair("Authorization", sign))
        }

        val request = builder.url(requestUrl).build()

        for (i in 0 .. 3) {
            try {
                return PenicillinResponse(this, request, session.httpClient.newCall(request).execute())

            } catch (e: ConnectException) {
                println("Connection failed. Try again in 3 secs.")
                e.printStackTrace()
                Thread.sleep(3000)
            }
        }

        throw TwitterAPIError("Failed to connect to $originalUrl", "")
    }
}
