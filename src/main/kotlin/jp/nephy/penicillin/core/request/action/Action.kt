package jp.nephy.penicillin.core.request.action

import io.ktor.client.request.HttpRequest
import io.ktor.client.request.request
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.isSuccess
import io.ktor.util.flattenEntries
import jp.nephy.jsonkt.*
import jp.nephy.jsonkt.delegation.byInt
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.exceptions.TwitterApiError
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import mu.KotlinLogging
import kotlin.reflect.KClass

internal val logger = KotlinLogging.logger("Penicillin.ApiAction")

internal suspend fun ApiAction<*>.execute(request: ApiRequest): Pair<HttpRequest, HttpResponse> {
    lateinit var lastException: Throwable

    repeat(session.option.maxRetries) {
        try {
            val response = session.httpClient.request<HttpResponse>(request.builder.finalize())
            return response.call.request to response
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            // TEMP FIX: Set-CookieConfig header format may be invalid like Sat, 5 Sep 2020 16:30:05 GMT
            if (e is IllegalStateException && e.message.orEmpty().startsWith("Invalid date length.")) {
                logger.debug(e) { LocalizedString.ApiRequestFailedLog.format(request.builder.url, it + 1, session.option.maxRetries) }
            } else {
                logger.error(e) { LocalizedString.ApiRequestFailedLog.format(request.builder.url, it + 1, session.option.maxRetries) }
            }

            lastException = e
        }

        if (it < session.option.maxRetries) {
            delay(session.option.retryInMillis)
        }
    }

    throw PenicillinLocalizedException(LocalizedString.ApiRequestFailed, cause = lastException, args = *arrayOf(request.builder.url))
}

internal fun checkError(request: HttpRequest, response: HttpResponse, content: String? = null) {
    logger.trace {
        buildString {
            appendln("${response.version} ${response.status.value} ${request.method.value} ${request.url}")

            val (requestHeaders, responseHeaders) = request.headers.flattenEntries() to response.headers.flattenEntries()
            val (longestRequestHeaderLength, longestResponseHeaderLength) = requestHeaders.maxBy { it.first.length }?.first.orEmpty().length + 1 to responseHeaders.maxBy { it.first.length }?.first.orEmpty().length + 1
            appendln("Request headers =\n${requestHeaders.joinToString("\n") { "    ${it.first.padEnd(longestRequestHeaderLength)}: ${it.second}" }}")
            appendln("Response headers =\n${responseHeaders.joinToString("\n") { "    ${it.first.padEnd(longestResponseHeaderLength)}: ${it.second}" }}\n")

            append(
                when {
                    content == null -> {
                        "(Streaming Response)"
                    }
                    content.isBlank() -> {
                        "(Empty Response)"
                    }
                    else -> {
                        content
                    }
                }
            )
        }
    }

    if (response.status.isSuccess()) {
        return
    }
    val json = content?.toJsonObjectSafe(true)
    if (json != null) {
        val error = json.getOrNull("errors")?.jsonArrayOrNull?.firstOrNull() ?: json.getOrNull("error")
        when (error) {
            is JsonObject -> {
                val code by error.byInt { -1 }
                val message by error.byString { "" }
                throw TwitterApiError(code, message, content, request, response)
            }
            is JsonPrimitive -> {
                throw TwitterApiError(-1, error.content, content, request, response)
            }
            else -> {
                throw PenicillinLocalizedException(LocalizedString.UnknownApiErrorWithStatusCode, request, response, null, response.status.value, content)
            }
        }
    }

    throw PenicillinLocalizedException(LocalizedString.ApiReturnedNon200StatusCode, request, response, null, response.status.value, response.status.description)
}

internal suspend fun HttpResponse.readTextSafe(): String? {
    val maxRetries = 3

    repeat(maxRetries) {
        try {
            return readText().trim().unescapeHTML()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            logger.error(e) { "Failed to read text. (${it + 1}/$maxRetries)\n${call.request.url}" }
        }
    }

    return null
}

internal fun String.toJsonObjectSafe(ignoreError: Boolean = false): JsonObject? {
    return try {
        toJsonObject()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        if (ignoreError) {
            return jsonObjectOf()
        }

        // TODO: throws
        logger.error(e) { LocalizedString.JsonParsingFailed.format(this) }
        null
    }
}

internal fun String.toJsonArraySafe(ignoreError: Boolean = false): JsonArray? {
    return try {
        toJsonArray()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        if (ignoreError) {
            return jsonArrayOf()
        }

        logger.error(e) { LocalizedString.JsonParsingFailed.format(this) }
        null
    }
}

internal fun <T: PenicillinModel> JsonObject.parseSafe(model: KClass<T>, content: String?): T? {
    return try {
        parse(model)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        logger.error(e) { LocalizedString.JsonModelCastFailed.format(model.simpleName, content) }
        null
    }
}

internal fun <T: PenicillinModel> JsonArray.parseSafe(model: KClass<T>, content: String?): List<T> {
    return try {
        parseList(model)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        logger.error(e) { LocalizedString.JsonModelCastFailed.format(model.simpleName, content) }
        emptyList()
    }
}

internal fun String.unescapeHTML(): String {
    return replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">")
}
