package jp.nephy.penicillin.api

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.google.gson.*
import jp.nephy.penicillin.api.model.EpochTime
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.exception.TwitterAPIError
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.net.URL
import java.util.*

enum class ResponseFormats {
    JSON
}

class Parameter {
    private val parameters = mutableMapOf<String,String?>()

    fun put(key: String, value: String?) {
        parameters.put(key, value)
    }

    fun toList(): List<Pair<String,String?>> {
        return parameters.toList()
    }
}

class RateLimit(val limit: Int, val remaining: Int, val resetAt: EpochTime) {
    fun isExceeded(): Boolean {
        return remaining == 0
    }

    fun getCurrentLimit(): Int {
        return limit - remaining
    }

    fun getResetAt(): Date {
        return resetAt.toDate()
    }
}

data class ResponseObject<out T>(val data: T, val request: Request, val response: Response, val error: FuelError?, val rateLimit: RateLimit) {
    fun print() {
        println(request.toString())
        println()
        println(response.toString())
        println()
        println("Ratelimit: ${rateLimit.remaining} / ${rateLimit.limit} (reset at ${rateLimit.resetAt.toDate()})")
    }
}
data class ResponseList<T>(val request: Request, val response: Response, val error: FuelError?, val rateLimit: RateLimit) : ArrayList<T>() {
    fun print() {
        println(request.toString())
        println()
        println(response.toString())
        println()
        println("Data size: ${this.size}")
        println("Ratelimit: ${rateLimit.remaining} / ${rateLimit.limit} (reset at ${rateLimit.resetAt.toDate()})")
    }
}

interface IEndPoint {
    val method: HTTPMethod
    val resourceUrl: String
    val responseFormat: ResponseFormats
    val isRateLimited: Boolean
    val defaultParameter: Parameter

    fun getResourceURL(): URL {
        return URL(resourceUrl)
    }
}

abstract class AbsOAuthEndPoint<T>(val oauth: OAuthRequestHandler): IEndPoint {
    fun parseAsObject(content: String?): JsonObject {
        return Gson().fromJson(content, JsonObject::class.java)
    }

    fun parseAsList(content: String?): JsonArray {
        return Gson().fromJson(content, JsonArray::class.java)
    }

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
            parseAsObject(content)
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
            parseAsList(content)
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

abstract class AbsOAuthGet<T>(oauth: OAuthRequestHandler): AbsOAuthEndPoint<T>(oauth) {
    override val method = HTTPMethod.GET
}

abstract class AbsOAuthPost<T>(oauth: OAuthRequestHandler): AbsOAuthEndPoint<T>(oauth) {
    override val method = HTTPMethod.POST
}

abstract class AbsOAuthDelete<T>(oauth: OAuthRequestHandler): AbsOAuthEndPoint<T>(oauth) {
    override val method = HTTPMethod.DELETE
}
