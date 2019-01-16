@file:Suppress("UNUSED")

package jp.nephy.penicillin.core.request

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent
import io.ktor.http.*
import io.ktor.util.InternalAPI
import io.ktor.util.appendAll
import io.ktor.util.flattenForEach
import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.core.auth.OAuthUtil
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.core.emulation.Tweetdeck
import jp.nephy.penicillin.core.emulation.Twitter4iPhone
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.body.EncodedFormContent
import jp.nephy.penicillin.core.request.body.MultiPartContent
import jp.nephy.penicillin.core.request.body.RequestBodyBuilder
import jp.nephy.penicillin.core.session.Session
import jp.nephy.penicillin.endpoints.EndpointHost
import jp.nephy.penicillin.endpoints.PrivateEndpoint
import mu.KotlinLogging
import java.util.*
import kotlin.collections.set

class ApiRequestBuilder(private val session: Session, private val httpMethod: HttpMethod, private val protocol: URLProtocol, private val host: EndpointHost, private val path: String) {
    companion object {
        private val logger = KotlinLogging.logger("Penicillin.RequestBuilder")
    }

    private var headers = HeadersBuilder()
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
        get() = URLBuilder(protocol = protocol, host = host.value, port = protocol.defaultPort, encodedPath = path, parameters = parameters.copy()).buildString()

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

    @UseExperimental(InternalAPI::class)
    private fun signRequest() {
        val signature = when (authorizationType) {
            AuthorizationType.OAuth1a -> {
                val authorizationHeaderComponent = linkedMapOf(
                    "oauth_signature" to null,
                    "oauth_callback" to oauthCallbackUrl,
                    "oauth_nonce" to OAuthUtil.randomUUID(),
                    "oauth_timestamp" to OAuthUtil.currentEpochTime(),
                    "oauth_consumer_key" to session.credentials.consumerKey!!,
                    "oauth_token" to session.credentials.accessToken,
                    "oauth_version" to "1.0",
                    "oauth_signature_method" to "HMAC-SHA1"
                )
                val signatureParam = sortedMapOf<String, String>().apply {
                    authorizationHeaderComponent.filterValues { it != null }.forEach {
                        put(it.key, it.value)
                    }
                    if (body !is MultiPartContent) {
                        val forms = (body as? EncodedFormContent)?.forms
                        val params = if (forms != null) {
                            parameters.copy().build() + forms
                        } else {
                            parameters.copy().build()
                        }

                        params.flattenForEach { key, value ->
                            put(key.encodeURLParameter(), value.encodeURLParameter())
                        }
                    }
                }
                val signatureParamString = OAuthUtil.signatureParamString(signatureParam)
                val signingKey = OAuthUtil.signingKey(session.credentials.consumerSecret!!, session.credentials.accessTokenSecret)
                val signatureBaseString =
                    OAuthUtil.signingBaseString(httpMethod, URLBuilder(protocol = protocol, host = host.value, port = protocol.defaultPort, encodedPath = path).buildString(), signatureParamString)
                authorizationHeaderComponent["oauth_signature"] = OAuthUtil.signature(signingKey, signatureBaseString)

                "OAuth ${authorizationHeaderComponent.filterValues { it != null }.toList().joinToString(", ") { "${it.first}=\"${it.second}\"" }}"
            }
            AuthorizationType.OAuth2 -> {
                "Bearer ${session.credentials.bearerToken!!}"
            }
            AuthorizationType.OAuth2RequestToken -> {
                "Basic ${Base64.getEncoder().encodeToString("${session.credentials.consumerKey!!.encodeOAuth()}:${session.credentials.consumerSecret!!.encodeOAuth()}".toByteArray())}"
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

        logger.trace { "Endpoint: ${javaClass.simpleName}#${method.name}" }
        val annotation = method.getAnnotation(PrivateEndpoint::class.java) ?: return
        if (session.option.emulationMode == EmulationMode.None || (annotation.mode.isNotEmpty() && session.option.emulationMode !in annotation.mode)) {
            throw PenicillinLocalizedException(LocalizedString.PrivateEndpointRequiresOfficialClientEmulation)
        }
    }

    internal fun build(): ApiRequest {
        checkEmulation()

        return ApiRequest(session, this)
    }

    private fun ParametersBuilder.copy(): ParametersBuilder {
        return ParametersBuilder().apply { appendAll(this@copy) }
    }
}
