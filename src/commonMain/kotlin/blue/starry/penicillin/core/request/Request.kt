/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

package blue.starry.penicillin.core.request

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.asJsonElement
import blue.starry.penicillin.core.emulation.EmulationMode
import blue.starry.penicillin.endpoints.common.TweetMode
import blue.starry.penicillin.extensions.session
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.buildJsonObject

/**
 * Url string.
 */
public val ApiRequestBuilder.url: String
    get() = URLBuilder(
        protocol = host.protocol,
        host = host.domain,
        port = host.port,
        parameters = parameters.copy().build()
    ).appendPathSegments(path).buildString()

/**
 * Creates "application/x-www-form-urlencoded" content.
 */
public fun ApiRequestBuilder.formBody(vararg pairs: Pair<String, Any?>, mode: EmulationMode? = null) {
    if (mode != null && mode != session.option.emulationMode) {
        return
    }

    for ((key, value) in pairs) {
        forms[key] = value?.toString() ?: continue
    }

    body = {
        FormDataContent(forms.copy().build())
    }
}

/**
 * Creates "multipart/form-data" content.
 */
public fun ApiRequestBuilder.multiPartBody(mode: EmulationMode? = null, block: FormBuilder.() -> Unit) {
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
public fun FormBuilder.append(vararg pairs: Pair<String, Any?>) {
    for ((key, value) in pairs) {
        append(key, value?.toString() ?: continue)
    }
}

/**
 * Creates "application/json" content.
 */
public fun ApiRequestBuilder.jsonBody(vararg pairs: Pair<String, Any?>, mode: EmulationMode? = null) {
    if (mode != null && mode != session.option.emulationMode) {
        return
    }

    val json = buildJsonObject {
        for ((key, value) in pairs) {
            if (value != null) {
                key to value.asJsonElement()
            }
        }
    }

    body = {
        JsonObjectContent(json)
    }
}

/**
 * Creates "application/json" content.
 */
public fun ApiRequestBuilder.jsonBody(json: JsonObject, mode: EmulationMode? = null) {
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
public fun ApiRequestBuilder.parameter(key: String, value: Any?, mode: EmulationMode? = null) {
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
public fun ApiRequestBuilder.parameters(vararg pairs: Pair<String, Any?>, mode: EmulationMode? = null) {
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
public fun ApiRequestBuilder.header(key: String, value: Any?, mode: EmulationMode? = null) {
    if (mode != null && session.option.emulationMode != mode) {
        return
    }

    headers[key] = value?.toString() ?: return
}

/**
 * Sets header keys with value, or update existing one with value.
 */
public fun ApiRequestBuilder.headers(vararg pairs: Pair<String, Any?>, mode: EmulationMode? = null) {
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
public fun ApiRequestBuilder.header(headers: Headers, mode: EmulationMode? = null) {
    if (mode != null && session.option.emulationMode != mode) {
        return
    }

    headers.flattenForEach { key, value ->
        header(key, value, mode)
    }
}

@OptIn(InternalAPI::class)
internal fun ParametersBuilder.copy(): ParametersBuilder {
    return ParametersBuilder().apply { appendAll(this@copy) }
}
