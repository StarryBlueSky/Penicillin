/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNUSED")

package jp.nephy.penicillin.core.request.action

import io.ktor.client.request.HttpRequest
import io.ktor.client.request.request
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.isSuccess
import io.ktor.util.flattenEntries
import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.JsonPrimitive
import jp.nephy.jsonkt.delegation.byNullableInt
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.jsonArrayOrNull
import jp.nephy.jsonkt.toJsonObjectOrNull
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.exceptions.throwApiError
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.extensions.session
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import mu.KotlinLogging

private val apiActionLogger = KotlinLogging.logger("Penicillin.ApiAction")

internal suspend fun ApiAction<*>.execute(): Pair<HttpRequest, HttpResponse> {
    lateinit var lastException: Throwable

    repeat(session.option.maxRetries) {
        try {
            val response = session.httpClient.request<HttpResponse>(request.builder.finalize())
            return response.call.request to response
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            if (e is PenicillinLocalizedException && e.localizedString == LocalizedString.SessionAlreadyClosed) {
                throw e
            }
            
            // TEMP FIX: Set-CookieConfig header format may be invalid like Sat, 5 Sep 2020 16:30:05 GMT
            if (e is IllegalStateException && e.message?.startsWith("Invalid date length.") == true) {
                apiActionLogger.debug(e) { LocalizedString.ApiRequestFailedLog.format(request.builder.url, it + 1, session.option.maxRetries) }
            } else {
                apiActionLogger.error(e) { LocalizedString.ApiRequestFailedLog.format(request.builder.url, it + 1, session.option.maxRetries) }
            }

            lastException = e
        }

        if (it + 1 < session.option.maxRetries) {
            delay(session.option.retryInMillis)
        }
    }

    throw PenicillinLocalizedException(LocalizedString.ApiRequestFailed, cause = lastException, args = *arrayOf(request.builder.url))
}

internal fun ApiAction<*>.checkError(request: HttpRequest, response: HttpResponse, content: String? = null) {
    apiActionLogger.trace {
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
    
    val json = content?.toJsonObjectOrNull()
    if (json != null) {
        when (val error = json.getOrNull("errors")?.jsonArrayOrNull?.firstOrNull() ?: json.getOrNull("error")) {
            is JsonObject -> {
                val code by error.byNullableInt
                val message by error.byString { "" }
                
                throwApiError(code, message, content, request, response)
            }
            is JsonPrimitive -> {
                throwApiError(null, error.content, content, request, response)
            }
            else -> {
                throw PenicillinLocalizedException(LocalizedString.UnknownApiErrorWithStatusCode, request, response, null, response.status.value, content)
            }
        }
    }

    throw PenicillinLocalizedException(LocalizedString.ApiReturnedNon200StatusCode, request, response, null, response.status.value, response.status.description)
}

internal suspend fun HttpResponse.readTextOrNull(): String? {
    return runCatching { 
        readText().unescapeHTML()
    }.getOrNull()
}

internal fun String.unescapeHTML(): String {
    return replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">")
}
