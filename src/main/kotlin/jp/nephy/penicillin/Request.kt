package jp.nephy.penicillin

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import java.net.URL

class TwitterAPIError(message: String): Exception(message)

open class RequestBase {
    private val gson = Gson()

    private fun parse(request: Request, response: Response, result: Result<String,FuelError>): JsonObject {
        val (content, error) = result

        println(request.toString())
        println()
        println(response.toString())
        return try {
            if (error !== null) {
                throw TwitterAPIError(String(response.data))
            }

            gson.fromJson(content, JsonObject::class.java)
        } catch (e: TwitterAPIError) {
            println(e.message)
            gson.fromJson("{}", JsonObject::class.java)
        } catch (e: JsonSyntaxException) {
            gson.fromJson("{}", JsonObject::class.java)
        }
    }

    protected fun httpGetAsJson(url: URL, header: Map<String,String>, data: Map<String,String>?=null): JsonObject {
        val (request, response, result) = Fuel.get(url.toString(), data?.toParamList()).header(header).responseString()
        return parse(
                request, response, result
        )
    }

    protected fun httpPostAsJson(url: URL, header: Map<String,String>, data: Map<String,String>?=null): JsonObject {
        val (request, response, result) = Fuel.post(url.toString(), data?.toParamList()).header(header).responseString()
        return parse(
                request, response, result
        )
    }

    protected fun httpDeleteAsJson(url: URL, header: Map<String,String>, data: Map<String,String>?=null): JsonObject {
        val (request, response, result) = Fuel.delete(url.toString(), data?.toParamList()).header(header).responseString()
        return parse(
                request, response, result
        )
    }
}

class OAuthRequest(private val ck: ConsumerKey, private val cs: ConsumerSecret, private val at: AccessToken, private val ats: AccessTokenSecret, private val uuid: String=Common.getRandomUUID(), private val deviceId: String=Common.getRandomUUID()): RequestBase() {
    fun send(method: HTTPMethod, url: URL, data: Map<String,String>?=null): JsonObject {
        val header: MutableMap<String,String> = OAuthRequestHeader(method, url, uuid, deviceId).authorize(ck, cs, at, ats, data).apply {
            if (method == HTTPMethod.POST) {
                setLength(data)
            }
        }.get()

        return when (method) {
            HTTPMethod.GET -> httpGetAsJson(url, header, data)
            HTTPMethod.POST -> httpPostAsJson(url, header, data)
            HTTPMethod.DELETE -> httpDeleteAsJson(url, header, data)
        }
    }
}

class BasicRequest(private val ck: ConsumerKey, private val cs: ConsumerSecret): RequestBase() {
    fun send(method: HTTPMethod, url:URL, data: Map<String,String>?=null): JsonObject {
        val header: MutableMap<String,String> = BasicRequestHeader(url).authorize(ck, cs).get()

        return when (method) {
            HTTPMethod.GET -> httpGetAsJson(url, header, data)
            HTTPMethod.POST -> httpPostAsJson(url, header, data)
            else -> throw NotImplementedError()
        }
    }
}

class BearerRequest(private val token: BearerToken): RequestBase() {
    fun send(method: HTTPMethod, url:URL, data: Map<String,String>?=null): JsonObject {
        val header: MutableMap<String,String> = BearerRequestHeader(url).authorize(token).get()

        return when (method) {
            HTTPMethod.GET -> httpGetAsJson(url, header, data)
            HTTPMethod.POST -> httpPostAsJson(url, header, data)
            else -> throw NotImplementedError()
        }
    }
}
