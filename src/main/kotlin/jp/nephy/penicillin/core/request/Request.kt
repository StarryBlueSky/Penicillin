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

package jp.nephy.penicillin.core.request

import io.ktor.client.request.forms.FormBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.util.appendAll
import io.ktor.util.flattenForEach
import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.asJsonElement
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.endpoints.common.TweetMode
import jp.nephy.penicillin.extensions.session
import kotlinx.serialization.json.json

/**
 * Url string.
 */
val ApiRequestBuilder.url: String
    get() = URLBuilder(protocol = host.protocol, host = host.domain, port = host.port, encodedPath = path, parameters = parameters.copy()).buildString()

/**
 * Creates "application/x-www-form-urlencoded" content.
 */
fun ApiRequestBuilder.formBody(vararg pairs: Pair<String, Any?>, mode: EmulationMode? = null) {
    if (mode != null && mode != session.option.emulationMode) {
        return
    }

    for ((key, value) in pairs) {
        forms[key] = value?.toString() ?: continue
    }

    body = {
        FormDataContent(forms.build())
    }
}

/**
 * Creates "multipart/form-data" content.
 */
fun ApiRequestBuilder.multiPartBody(mode: EmulationMode? = null, block: FormBuilder.() -> Unit) {
    if (mode != null && mode != session.option.emulationMode) {
        return
    }

    val parts = formData(block)

    body = {
        MultiPartFormDataContent(parts)
    }
}

/**
 * Appends pairs.
 */
fun FormBuilder.append(vararg pairs: Pair<String, Any?>) {
    for ((key, value) in pairs) {
        append(key, value?.toString() ?: continue)
    }
}

/**
 * Creates "application/json" content.
 */
fun ApiRequestBuilder.jsonBody(vararg pairs: Pair<String, Any?>, mode: EmulationMode? = null) {
    if (mode != null && mode != session.option.emulationMode) {
        return
    }

    val json = json {
        for ((key, value) in pairs) {
            key to value.asJsonElement()
        }
    }

    body = {
        JsonObjectContent(json)
    }
}

/**
 * Creates "application/json" content.
 */
fun ApiRequestBuilder.jsonBody(json: JsonObject, mode: EmulationMode? = null) {
    if (mode != null && mode != session.option.emulationMode) {
        return
    }

    body = {
        JsonObjectContent(json)
    }
}

/**
 * Sets a parameter key with value, or update existing one with value.
 */
fun ApiRequestBuilder.parameter(key: String, value: Any?, mode: EmulationMode? = null) {
    if (mode != null && session.option.emulationMode != mode) {
        return
    }

    when {
        key == "tweet_mode" && value is TweetMode? -> {
            parameters[key] = value?.value ?: session.option.defaultTweetMode.value ?: return
        }
        value is EnumRequestParameter -> {
            parameters[key] = value.value ?: return
        }
        else -> {
            parameters[key] = value?.toString() ?: return
        }
    }
}

/**
 * Sets parameter keys with value, or update existing one with value.
 */
fun ApiRequestBuilder.parameters(vararg pairs: Pair<String, Any?>, mode: EmulationMode? = null) {
    if (mode != null && session.option.emulationMode != mode) {
        return
    }

    for ((first, second) in pairs) {
        parameter(first, second, mode)
    }
}

/**
 * Sets a header key with value, or update existing one with value.
 */
fun ApiRequestBuilder.header(key: String, value: Any?, mode: EmulationMode? = null) {
    if (mode != null && session.option.emulationMode != mode) {
        return
    }

    headers[key] = value?.toString() ?: return
}

/**
 * Sets header keys with value, or update existing one with value.
 */
fun ApiRequestBuilder.headers(vararg pairs: Pair<String, Any?>, mode: EmulationMode? = null) {
    if (mode != null && session.option.emulationMode != mode) {
        return
    }

    for ((first, second) in pairs) {
        header(first, second, mode)
    }
}

/**
 * Adds existing [Headers] instance to headers.
 */
fun ApiRequestBuilder.header(headers: Headers, mode: EmulationMode? = null) {
    if (mode != null && session.option.emulationMode != mode) {
        return
    }

    headers.flattenForEach { key, value ->
        header(key, value, mode)
    }
}

internal fun ParametersBuilder.copy(): ParametersBuilder {
    return ParametersBuilder().apply { appendAll(this@copy) }
}
