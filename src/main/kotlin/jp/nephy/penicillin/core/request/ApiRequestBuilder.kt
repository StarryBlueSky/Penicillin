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

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent
import io.ktor.http.*
import io.ktor.util.appendAll
import io.ktor.util.flattenForEach
import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.core.auth.OAuthUtil
import jp.nephy.penicillin.core.auth.encodeBase64
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.core.emulation.Tweetdeck
import jp.nephy.penicillin.core.emulation.Twitter4iPhone
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.body.RequestBodyBuilder
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.endpoints.PrivateEndpoint
import jp.nephy.penicillin.extensions.session
import mu.KotlinLogging
import kotlin.collections.set

private val apiRequestBuilderLogger = KotlinLogging.logger("Penicillin.RequestBuilder")

class ApiRequestBuilder(val client: ApiClient, private val httpMethod: HttpMethod, private val host: EndpointHost, private val path: String) {
    private var headers = HeadersBuilder()
    
    @Suppress("MemberVisibilityCanBePrivate")
    fun header(key: String, value: Any?, emulationMode: EmulationMode? = null) {
        if (emulationMode != null && session.option.emulationMode != emulationMode) {
            return
        }

        headers[key] = value?.toString() ?: return
    }

    fun header(vararg pairs: Pair<String, Any?>, emulationMode: EmulationMode? = null) {
        for ((first, second) in pairs) {
            header(first, second, emulationMode)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun header(headers: Headers, emulationMode: EmulationMode? = null) {
        headers.flattenForEach { key, value ->
            header(key, value, emulationMode)
        }
    }

    private var authorizationType = AuthorizationType.OAuth1a
    private var oauthCallbackUrl: String? = null
    fun authType(type: AuthorizationType, callbackUrl: String? = null) {
        authorizationType = type
        oauthCallbackUrl = callbackUrl
    }

    private val parameters = ParametersBuilder()
    fun parameter(key: String, value: Any?, emulationMode: EmulationMode? = null) {
        if (emulationMode != null && session.option.emulationMode != emulationMode) {
            return
        }

        parameters[key] = value?.toString() ?: return
    }

    fun parameter(vararg pairs: Pair<String, Any?>, emulationMode: EmulationMode? = null) {
        for ((first, second) in pairs) {
            parameter(first, second, emulationMode)
        }
    }

    private var body: Any = EmptyContent
    fun body(builder: RequestBodyBuilder.() -> Unit) {
        body = RequestBodyBuilder(session.option.emulationMode).apply(builder).build()
    }

    val url: String
        get() = URLBuilder(protocol = host.protocol, host = host.domain, port = host.protocol.defaultPort, encodedPath = path, parameters = parameters.copy()).buildString()

    internal fun finalize(): (HttpRequestBuilder) -> Unit {
        when (session.option.emulationMode) {
            EmulationMode.TwitterForiPhone -> {
                if (authorizationType == AuthorizationType.OAuth1a) {
                    val emulation = Twitter4iPhone(session)
                    header(emulation.headers)
                    header("kdt", session.credentials.knownDeviceToken)
                }
            }
            EmulationMode.Tweetdeck -> {
                authType(AuthorizationType.OAuth2)
                val emulation = Tweetdeck(session)
                header(emulation.headers)
            }
            else -> {
            }
        }

        signRequest()

        return {
            it.method = httpMethod
            it.url(url)
            it.headers.appendAll(headers)
            it.body = body
        }
    }
    
    private fun signRequest() {
        val signature = when (authorizationType) {
            AuthorizationType.OAuth1a -> {
                val authorizationHeaderComponent = OAuthUtil.initialAuthorizationHeaderComponents(
                    oauthCallbackUrl, consumerKey = session.credentials.consumerKey, accessToken = session.credentials.accessToken
                )
                val signatureParam = OAuthUtil.signatureParam(authorizationHeaderComponent, body, parameters)
                val signatureParamString = OAuthUtil.signatureParamString(signatureParam)
                val signingKey = OAuthUtil.signingKey(session.credentials.consumerSecret!!, session.credentials.accessTokenSecret)
                val signatureBaseString = OAuthUtil.signingBaseString(
                    httpMethod, URLBuilder(protocol = host.protocol, host = host.domainForSigning ?: host.domain, encodedPath = path).build(), signatureParamString
                )
                authorizationHeaderComponent["oauth_signature"] = OAuthUtil.signature(signingKey, signatureBaseString)

                "OAuth ${authorizationHeaderComponent.filterValues { it != null }.toList().joinToString(", ") { "${it.first}=\"${it.second}\"" }}"
            }
            AuthorizationType.OAuth2 -> {
                "Bearer ${session.credentials.bearerToken!!}"
            }
            AuthorizationType.OAuth2RequestToken -> {
                "Basic ${"${session.credentials.consumerKey!!.encodeOAuth()}:${session.credentials.consumerSecret!!.encodeOAuth()}".encodeBase64()}"
            }
            AuthorizationType.None -> null
        } ?: return

        headers[HttpHeaders.Authorization] = signature
    }
    
    private fun checkEmulation() {
        if (session.option.skipEmulationChecking) {
            return
        }

        val trace = Thread.currentThread().stackTrace.find {
            it.className.startsWith("jp.nephy.penicillin.endpoints")
        } ?: return
        val javaClass = javaClass.classLoader.loadClass(trace.className)
        val method = javaClass.methods.find { it.name == trace.methodName } ?: return

        apiRequestBuilderLogger.trace { "Endpoint: ${javaClass.canonicalName}#${method.name}" }
        val annotation = method.getAnnotation(PrivateEndpoint::class.java) ?: return
        if (session.option.emulationMode == EmulationMode.None || (annotation.modes.isNotEmpty() && session.option.emulationMode !in annotation.modes)) {
            throw PenicillinLocalizedException(LocalizedString.PrivateEndpointRequiresOfficialClientEmulation)
        }
    }

    internal fun build(): ApiRequest {
        checkEmulation()

        return ApiRequest(client, this)
    }
}

internal fun ParametersBuilder.copy(): ParametersBuilder {
    return ParametersBuilder().apply { appendAll(this@copy) }
}
