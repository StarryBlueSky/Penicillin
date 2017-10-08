package jp.nephy.penicillin

import com.github.salomonbrys.kotson.get
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

class PenicillinResponse(val request: Request, val response: Response) {
    fun checkError(content: String) {
        if (! response.isSuccessful) {
            try {
                val obj = Gson().fromJson(content, JsonObject::class.java)
                if (! obj["errors"].isJsonNull) {
                    val error = obj["errors"]

                    if (error.isJsonArray) {
                        if (error.asJsonArray.size() == 0) {
                            throw TwitterAPIError("errors size is 0 with HTTP code ${response.code()}.", content)
                        }
                        throw TwitterAPIError("API returned error with HTTP code ${response.code()}.", content, error.asJsonArray[0]["code"].asInt, error.asJsonArray[0]["message"].asString)
                    } else {
                        try {
                            throw TwitterAPIError(error.asString, content)
                        } catch (e: UnsupportedOperationException) {}

                        throw TwitterAPIError("API returned unknown error with HTTP code ${response.code()}.", content)
                    }
                } else if (! obj["error"].isJsonNull) {
                    throw TwitterAPIError("API returned error with HTTP code ${response.code()}.", content, null, obj["error"].asJsonObject["message"].asString)
                } else {
                    throw TwitterAPIError("API returned unknown error with HTTP code ${response.code()}.", content)
                }
            } catch (e: JsonSyntaxException) {
                throw TwitterAPIError("API returned unknown error.", content)
            }
        }
    }

    fun getContent() = response.body()?.string() ?: ""

    fun <T> getResponseStream() = ResponseStream<T>(request, response)

    fun getResponseText(): ResponseText {
        val content = getContent()
        checkError(content)
        return ResponseText(content, request, response, RateLimit(response.headers()))
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseObject(): ResponseObject<T> {
        val content = getContent()

        checkError(content)

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

        return ResponseObject(result, content, request, response, RateLimit(response.headers()))
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseList(): ResponseList<T> {
        val content = getContent()

        checkError(content)

        val json = try {
            Gson().fromJson(content, JsonArray::class.java)
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
