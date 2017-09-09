package jp.nephy.penicillin.api

import com.google.gson.*
import jp.nephy.penicillin.api.model.EpochTime
import jp.nephy.penicillin.request.exception.TwitterAPIError
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

abstract class AbsOAuthEndPoint<T>(val oauth: OAuthRequestHandler): IEndPoint {
    fun getRateLimit(header: Map<String,List<String>>): RateLimit {
        return RateLimit(
                limit=header["x-rate-limit-limit"]?.get(0)?.toIntOrNull() ?: 0,
                remaining=header["x-rate-limit-remaining"]?.get(0)?.toIntOrNull() ?: 0,
                resetAt= EpochTime(header["x-rate-limit-reset"]?.get(0)?.toLongOrNull() ?: 0)
        )
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getAsObject(data: Map<String,String>?): ResponseObject<T> {
        val (request, response, result) = oauth.send(method, getResourceURL(), data)
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
                rateLimit=getRateLimit(response.httpResponseHeaders)
        )
    }

    @Throws(TwitterAPIError::class)
    inline fun <reified T> getAsList(data: Map<String,String>?): ResponseList<T> {
        val (request, response, result) = oauth.send(method, getResourceURL(), data)
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
                rateLimit=getRateLimit(response.httpResponseHeaders)
        ).apply {
            jsonArray.forEach {
                add(T::class.java.getConstructor(JsonElement::class.java).newInstance(it))
            }
        }
    }
}
