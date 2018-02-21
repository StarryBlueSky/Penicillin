package jp.nephy.penicillin

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import jp.nephy.jsonkt.JsonKt
import jp.nephy.jsonkt.get
import jp.nephy.jsonkt.int
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.exception.TwitterAPIError
import jp.nephy.penicillin.misc.unescapeHTMLCharacters
import jp.nephy.penicillin.response.*
import okhttp3.Request
import okhttp3.Response
import java.lang.reflect.InvocationTargetException

class PenicillinResponse(val prevRequest: PenicillinRequest, val request: Request, val response: Response) {
    private var content: String? = null

    fun checkError(content: String) {
        if (! response.isSuccessful) {
            try {
                val obj = JsonKt.toJsonObject(content)
                if (! obj["errors"].isJsonNull) {
                    val error = obj["errors"]

                    if (error.isJsonArray) {
                        if (error.asJsonArray.size() == 0) {
                            throw TwitterAPIError("errors size is 0 with HTTP code ${response.code()}.", content)
                        }
                        throw TwitterAPIError("API returned error with HTTP code ${response.code()}.", content, error.asJsonArray[0]["code"].int, error.asJsonArray[0]["message"].string)
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

    fun getContent(): String {
        if (content == null) {
            content = (response.body()?.string() ?: "").unescapeHTMLCharacters()
        }
        return content!!
    }

    fun <T> getResponseStream() = ResponseStream<T>(request, response)

    fun getResponseText(): ResponseText {
        val content = getContent()
        checkError(content)
        return ResponseText(content, request, response)
    }

    fun getJsonObject(content: String) = try {
        JsonKt.toJsonObject(content)
    } catch (e: JsonSyntaxException) {
        e.printStackTrace()
        throw TwitterAPIError("Invalid Json format returned.", content)
    }!!

    fun getJsonArray(content: String) = try {
        Gson().fromJson(content, JsonArray::class.java)
    } catch (e: JsonSyntaxException) {
        e.printStackTrace()
        throw TwitterAPIError("Invalid Json format returned.", content)
    }!!

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseObject(): ResponseObject<T> {
        val content = getContent()
        checkError(content)

        val jsonObject = getJsonObject(content)
        val result = try {
            T::class.java.getConstructor(JsonElement::class.java).newInstance(jsonObject)
        } catch (e: NullPointerException) {
            e.printStackTrace()
            throw TwitterAPIError("Json is not matched with ${T::class.java.simpleName} Class.", content)
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            throw TwitterAPIError("Json is null. Use Empty class instead of ${T::class.java.simpleName}.", content)
        }

        return ResponseObject(result, content, request, response)
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseCursorObject(): ResponseCursorObject<T> {
        val response = getResponseObject<T>()
        return ResponseCursorObject(T::class.java, response.result, response.content, prevRequest, response.request, response.response)
    }

    @Throws(TwitterAPIError::class)
    fun <T> getResponseCursorObjectByClass(klass: Class<T>): ResponseCursorObject<T> {
        val content = getContent()
        checkError(content)

        val jsonObject = getJsonObject(content)
        val result = klass.getConstructor(JsonElement::class.java).newInstance(jsonObject)
        return ResponseCursorObject(klass, result, getContent(), prevRequest, request, response)
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getResponseList(): ResponseList<T> {
        val content = getContent()
        checkError(content)

        val jsonArray = getJsonArray(content)
        return ResponseList<T>(content, request, response).apply {
            jsonArray.forEach {
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
