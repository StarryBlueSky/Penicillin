package jp.nephy.penicillin.api

import com.google.gson.*
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.net.URL

class OAuthRequest(val oauth: OAuthRequestHandler, val method: HTTPMethod, private val path: String, val sendAsRaw: Boolean=false) {
    private val API_ROOT = "https://api.twitter.com/1.1"

    fun getResourceURL(): URL {
        return if (path.startsWith("/")) {
            URL("$API_ROOT$path")
        } else {
            URL(path)
        }
    }

    inline fun <reified T> getResponseObject(data: Array<out Pair<String, String>>?, file: ByteArray?=null): ResponseObject<T?> {
        val (request, response, result) = oauth.send(method, getResourceURL(), data?.toMap(), file, sendAsRaw)
        val (content, error) = result

        val jsonObject = try {
            Gson().fromJson(content, JsonObject::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
        val d = if (jsonObject != null) {
            T::class.java.getConstructor(JsonElement::class.java).newInstance(jsonObject)
        } else {
            null
        }

        return ResponseObject(
                data=d,
                request=request,
                response=response,
                error=error,
                rateLimit=RateLimit(response.headers)
        )
    }

    inline fun <reified T> getResponseList(data: Array<out Pair<String, String>>?): ResponseList<T?> {
        val (request, response, result) = oauth.send(method, getResourceURL(), data?.toMap())
        val (content, error) = result

        val jsonArray = try {
            Gson().fromJson(content, JsonArray::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }

        return ResponseList<T?>(
                request=request,
                response=response,
                error=error,
                rateLimit= RateLimit(response.headers)
        ).apply {
            jsonArray?.forEach {
                add(T::class.java.getConstructor(JsonElement::class.java).newInstance(it))
            }
        }
    }
}
