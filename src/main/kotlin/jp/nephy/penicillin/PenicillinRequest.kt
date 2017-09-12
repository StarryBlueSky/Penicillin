package jp.nephy.penicillin

import com.google.gson.Gson
import jp.nephy.penicillin.annotation.MustBeCalled
import jp.nephy.penicillin.misc.AuthorizationType
import jp.nephy.penicillin.misc.HTTPMethod
import jp.nephy.penicillin.misc.Util
import jp.nephy.penicillin.misc.toURLEncode
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody

class PenicillinRequest(private val session: Session) {
    private val builder = Request.Builder()
    private var method: HTTPMethod? = null
    private var url: String? = null
    private var params = mutableListOf<Pair<String, String>>()
    private var data = mutableListOf<Pair<String, String>>()
    private var body: RequestBody? = null
    private var hasFile: Boolean = false

    private val defaultHeaders = listOf(
            "X-Twitter-Client-Version" to "6.59.3",
            "Accept" to "*/*",
            "X-Client-UUID" to Util.getRandomUUID(),
            "X-Twitter-Client-Language" to "ja",
            "X-B3-TraceId" to Util.getB3TraceId(),
            "Accept-Language" to "ja",
            "X-Twitter-Client-DeviceID" to Util.getRandomUUID(),
            "User-Agent" to "Twitter-iPhone/6.59.3 iOS/9.3.3 (Apple;iPhone8,2;;;;;1)",
            "X-Twitter-Client-Limit-Ad-Tracking" to "1",
            "X-Twitter-API-Version" to "5",
            "X-Twitter-Client" to "Twitter-iPhone"
    )

    private var authorizationType = AuthorizationType.OAuth
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

    private fun expandParameters(vararg params: Pair<String, Any?>?) = params.filterNotNull().filter { it.second != null }.map {
        "${it.first.toURLEncode()}=${it.second!!.toString().toURLEncode()}"
    }.joinToString("&")

    fun param(param: Pair<String, Any?>?) = this.apply {
        if (param != null) {
            if (param.second == null) {
                return@apply
            }
            params.add(Pair(param.first, param.second!!.toString()))
        }
    }

    fun params(vararg params: Pair<String, Any?>?) = this.apply {
        params.forEach {
            param(it)
        }
    }

    fun dataAsForm(vararg data: Pair<String, Any?>?) = this.apply {
        body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), expandParameters(*data))
    }

    fun dataAsJson(vararg data: Pair<String, String>?) = this.apply {
        body = RequestBody.create(MediaType.parse("application/json"), Gson().toJson(data))
    }

    fun file(file: ByteArray, contentType: String, name: String="media") = this.apply {
        hasFile = true
        body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .apply {
                    data.forEach {
                        addFormDataPart(it.first, it.second.toURLEncode())
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
        builder.post(body)
    }.execute()

    fun delete() = this.apply {
        method = HTTPMethod.DELETE
        builder.delete()
    }.execute()

    private fun execute(): PenicillinResponse {
        if (params.isNotEmpty()) {
            url += "?${expandParameters(*params.toTypedArray())}"
        }
        if (url == null) {
            throw IllegalStateException("url must be non-null.")
        }

        headers(defaultHeaders)
        val sign = when (authorizationType) {
            AuthorizationType.OAuth -> session.oauth!!.sign(method!!, url!!, data + params, hasFile)
            AuthorizationType.Basic -> session.basic!!.sign()
            AuthorizationType.Bearer -> session.bearer!!.sign()
            AuthorizationType.NONE -> null
        }
        if (sign != null) {
            header(Pair("Authorization", sign))
        }

        val request = builder.url(url).build()
        return PenicillinResponse(session.client, request, session.httpClient.newCall(request).execute())
    }
}