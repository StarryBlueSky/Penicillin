package jp.nephy.penicillin.api

import com.google.gson.*
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.lang.reflect.InvocationTargetException
import java.net.URL

class OAuthRequest(val oauth: OAuthRequestHandler, val method: HTTPMethod, private val path: String) {
    val gson = Gson()
    private val apiRoot = "https://api.twitter.com/1.1"

    fun getResourceURL(): URL {
        return if (path.startsWith("/")) {
            URL("$apiRoot$path")
        } else {
            URL(path)
        }
    }

    fun getResponseStream(data: Array<out Pair<String, String>>?): ResponseStream {
        val (request, response) = oauth.send(method, getResourceURL(), data?.toMap())
        return ResponseStream(request, response)
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseObject(data: Array<out Pair<String, String>>?, file: ByteArray?=null, contentType: String?=null): ResponseObject<T> {
        val (request, response) = oauth.send(method, getResourceURL(), data?.toMap(), file, contentType)
        val content = response.body()?.string() ?: ""

        if (!response.isSuccessful) {
            println(request)
            println(request.headers())
            throw TwitterAPIError("API returned errors.", content)
        }

        val jsonObject = try {
            gson.fromJson(content, JsonObject::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            throw TwitterAPIError("Invalid Json format returned.", content)
        }

        val result = try {
            T::class.java.getConstructor(JsonElement::class.java).newInstance(jsonObject)
        } catch (e: NullPointerException) {
            e.printStackTrace()
            throw TwitterAPIError("Json is not matched with ${T::class.java.simpleName} Class.", content)
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            throw TwitterAPIError("Json is null. Use Empty class instead of ${T::class.java.simpleName}.", content)
        }

        return ResponseObject(result, content, request, response, RateLimit(response.headers()))
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseList(data: Array<out Pair<String, String>>?, file: ByteArray?=null, contentType: String?=null): ResponseList<T> {
        val (request, response) = oauth.send(method, getResourceURL(), data?.toMap(), file, contentType)
        val content = response.body()?.string() ?: ""

        if (!response.isSuccessful) {
            throw TwitterAPIError("API returned errors.", content)
        }

        val json = try {
            gson.fromJson(content, JsonArray::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            throw TwitterAPIError("Invalid Json format returned.", content)
        }

        return ResponseList<T>(content, request, response, RateLimit(response.headers())).apply {
            json.forEach {
                try {
                    add(T::class.java.getConstructor(JsonElement::class.java).newInstance(it))
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                    throw TwitterAPIError("Json is not matched with ${T::class.java.simpleName} Class.", content)
                }
            }
        }
    }
}
