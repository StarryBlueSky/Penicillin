package jp.nephy.penicillin

import com.google.gson.*
import jp.nephy.penicillin.exception.TwitterAPIError
import jp.nephy.penicillin.misc.RateLimit
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject
import jp.nephy.penicillin.response.ResponseStream
import jp.nephy.penicillin.response.ResponseText
import okhttp3.Request
import okhttp3.Response
import java.lang.reflect.InvocationTargetException

class PenicillinResponse(val client: Client, val request: Request, val response: Response) {
    fun getContent() = response.body()?.string() ?: ""

    fun getResponseStream() = ResponseStream(client, request, response)

    fun getResponseText() = ResponseText(getContent(), client, request, response, RateLimit(response.headers()))

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseObject(): ResponseObject<T> {
        val content = getContent()

        if (!response.isSuccessful) {
            println(request)
            println(request.headers())
            throw TwitterAPIError("API returned errors.", content)
        }

        val jsonObject = try {
            Gson().fromJson(content, JsonObject::class.java)
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

        return ResponseObject(result, content, client, request, response, RateLimit(response.headers()))
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseList(): ResponseList<T> {
        val content = getContent()

        if (!response.isSuccessful) {
            throw TwitterAPIError("API returned errors.", content)
        }

        val json = try {
            Gson().fromJson(content, JsonArray::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            throw TwitterAPIError("Invalid Json format returned.", content)
        }

        return ResponseList<T>(content, client, request, response, RateLimit(response.headers())).apply {
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
