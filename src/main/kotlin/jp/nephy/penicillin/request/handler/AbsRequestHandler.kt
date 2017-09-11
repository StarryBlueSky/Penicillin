package jp.nephy.penicillin.request.handler

import com.google.gson.Gson
import jp.nephy.penicillin.request.header.AbsRequestHeader
import jp.nephy.penicillin.request.toURLEncode
import okhttp3.*
import java.net.URL
import java.util.concurrent.TimeUnit

abstract class AbsRequestHandler {
    private val client = OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS).build()

    private fun expandParameters(parameters: Map<String,String>?) = parameters?.map { "${it.key.toURLEncode()}=${it.value.toURLEncode()}" }?.joinToString("&") ?: ""

    private fun buildURL(baseURL: URL, parameters: Map<String,String>?): URL {
        return if (parameters == null || parameters.isEmpty()) {
            baseURL
        } else {
            URL("$baseURL?" + expandParameters(parameters))
        }
    }

    protected fun httpGet(url: URL, header: AbsRequestHeader, data: Map<String,String>?): Pair<Request, Response> {
        val request = Request.Builder()
                .method("GET", null)
                .get()
                .url(buildURL(url, data))
                .headers(header.get())
                .build()
        val response = client.newCall(request).execute()
        return Pair(request, response)
    }

    protected fun httpPostAsForm(url: URL, header: AbsRequestHeader, data: Map<String,String>?): Pair<Request, Response> {
        val body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), expandParameters(data))
        val request = Request.Builder()
                .post(body)
                .url(url)
                .headers(header.get())
                .build()
        val response = client.newCall(request).execute()
        return Pair(request, response)
    }

    protected fun httpPostAsJson(url: URL, header: AbsRequestHeader, data: Map<String,String>?): Pair<Request, Response> {
        val body = RequestBody.create(MediaType.parse("application/json"), Gson().toJson(data))
        val request = Request.Builder()
                .post(body)
                .url(url)
                .headers(header.get())
                .build()
        val response = client.newCall(request).execute()
        return Pair(request, response)
    }

    protected fun httpPostAsMultiPart(url: URL, header: AbsRequestHeader, file: ByteArray, contentType: String, data: Map<String,String>?): Pair<Request, Response> {
        val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .apply {
                    data?.forEach {
                        addFormDataPart(it.key, it.value.toURLEncode())
                    }
                }
                .addFormDataPart("media", "blob", RequestBody.create(MediaType.parse(contentType), file))
                .build()
        val request = Request.Builder()
                .post(body)
                .url(url)
                .headers(header.get())
                .build()
        val response = client.newCall(request).execute()
        return Pair(request, response)
    }

    protected fun httpDelete(url: URL, header: AbsRequestHeader, data: Map<String,String>?): Pair<Request, Response> {
        val request = Request.Builder()
                .delete()
                .url(buildURL(url, data))
                .headers(header.get())
                .build()
        val response = client.newCall(request).execute()
        return Pair(request, response)
    }
}
