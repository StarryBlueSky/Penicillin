package jp.nephy.penicillin.api

import com.google.gson.*
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.exception.TwitterAPIError
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.net.URL

class OAuthRequest(val oauth: OAuthRequestHandler, val method: HTTPMethod, private val path: String) {
    private val API_ROOT = "https://api.twitter.com/1.1"

    fun getResourceURL(): URL {
        return if (path.startsWith("/")) {
            URL("$API_ROOT$path")
        } else {
            URL(path)
        }
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseObject(data: Array<out Pair<String, String>>?): ResponseObject<T> {
        val (request, response, result) = oauth.send(method, getResourceURL(), data?.toMap())
        val (content, error) = result

        if (error !== null) {
            throw TwitterAPIError(String(response.data))
        }
        val jsonObject = try {
            Gson().fromJson(content, JsonObject::class.java)
        } catch (e: JsonSyntaxException) {
            throw TwitterAPIError(String(response.data))
        }

        return ResponseObject(
                data=T::class.java.getConstructor(JsonElement::class.java).newInstance(jsonObject),
                request=request,
                response=response,
                error=error,
                rateLimit=RateLimit(response.httpResponseHeaders)
        )
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseList(data: Array<out Pair<String, String>>?): ResponseList<T> {
        val (request, response, result) = oauth.send(method, getResourceURL(), data?.toMap())
        val (content, error) = result

        if (error !== null) {
            throw TwitterAPIError(String(response.data))
        }
        val jsonArray = try {
            Gson().fromJson(content, JsonArray::class.java)
        } catch (e: JsonSyntaxException) {
            throw TwitterAPIError(String(response.data))
        }

        return ResponseList<T>(
                request=request,
                response=response,
                error=error,
                rateLimit= RateLimit(response.httpResponseHeaders)
        ).apply {
            jsonArray.forEach {
                add(T::class.java.getConstructor(JsonElement::class.java).newInstance(it))
            }
        }
    }
}
