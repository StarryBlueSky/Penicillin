@file:Suppress("UNUSED")

package jp.nephy.penicillin.core

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent
import io.ktor.http.*
import io.ktor.http.content.OutgoingContent
import io.ktor.util.appendAll
import io.ktor.util.flattenEntries
import io.ktor.util.flattenForEach
import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.asJsonElement
import jp.nephy.jsonkt.toJsonString
import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.core.auth.OAuthUtil
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.core.emulation.Tweetdeck
import jp.nephy.penicillin.core.emulation.Twitter4iPhone
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.endpoints.EndpointHost
import jp.nephy.penicillin.endpoints.PrivateEndpoint
import kotlinx.coroutines.io.ByteWriteChannel
import kotlinx.coroutines.io.writeFully
import kotlinx.coroutines.io.writeStringUtf8
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.json
import mu.KotlinLogging
import java.util.*
import kotlin.collections.set

private val logger = KotlinLogging.logger("Penicillin.RequestBuilder")

class PenicillinRequestBuilder(private val session: Session, private val httpMethod: HttpMethod, private val protocol: URLProtocol, private val host: EndpointHost, private val path: String) {
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

    internal fun build(): PenicillinRequest {
        checkEmulation()

        return PenicillinRequest(session, this)
    }
}

internal fun ParametersBuilder.copy(): ParametersBuilder {
    return ParametersBuilder().apply { appendAll(this@copy) }
}

class RequestBodyBuilder(private val emulationMode: EmulationMode) {
    private var encodedForm: EncodedFormContent? = null
    fun form(builder: EncodedFormContent.Builder.() -> Unit) {
        encodedForm = EncodedFormContent.Builder(emulationMode).apply(builder).build()
    }

    private var multiPart: MultiPartContent? = null
    fun multiPart(builder: MultiPartContent.Builder.() -> Unit) {
        multiPart = MultiPartContent.Builder().apply(builder).build()
    }

    private var json: JsonTextContent? = null
    fun json(builder: JsonTextContent.Builder.() -> Unit) {
        json = JsonTextContent.Builder().apply(builder).build()
    }

    internal fun build(): Any {
        return encodedForm ?: multiPart ?: json ?: EmptyContent
    }
}

class EncodedFormContent(val forms: Parameters): OutgoingContent.WriteChannelContent() {
    override val contentType = ContentType.Application.FormUrlEncoded.withCharset(Charsets.UTF_8)

    override suspend fun writeTo(channel: ByteWriteChannel) {
        channel.writeStringUtf8(forms.formUrlEncode())
    }

    class Builder(private val emulationMode: EmulationMode) {
        private val forms = ParametersBuilder()

        fun add(key: String, value: Any?, mode: EmulationMode? = null) {
            if (mode != null && emulationMode != mode) {
                return
            }

            forms[key] = value?.toString() ?: return
        }

        fun add(vararg pairs: Pair<String, Any?>, emulationMode: EmulationMode? = null) {
            for ((first, second) in pairs) {
                add(first, second, emulationMode)
            }
        }

        internal fun build(): EncodedFormContent {
            return EncodedFormContent(forms.build())
        }
    }
}

// From https://github.com/ktorio/ktor-samples/blob/183dd65e39565d6d09682a9b273937013d2124cc/other/client-multipart/src/MultipartApp.kt#L57
class MultiPartContent(private val parts: List<Part>): OutgoingContent.WriteChannelContent() {
    private val boundary = "***Penicillin-${UUID.randomUUID()}-Penicillin-${System.currentTimeMillis()}***"
    override val contentType = ContentType.MultiPart.FormData.withParameter("boundary", boundary).withCharset(Charsets.UTF_8)

    override suspend fun writeTo(channel: ByteWriteChannel) {
        for (part in parts) {
            channel.writeStringUtf8("--$boundary\r\n")
            val partHeaders = Headers.build {
                if (part.filename != null) {
                    append(HttpHeaders.ContentDisposition, "form-data; name=\"${part.name}\"; filename=\"${part.filename}\"")
                } else {
                    append(HttpHeaders.ContentDisposition, "form-data; name=\"${part.name}\"")
                }
                appendAll(part.headers)
            }
            for ((key, value) in partHeaders.flattenEntries()) {
                channel.writeStringUtf8("$key: $value\r\n")
            }
            channel.writeStringUtf8("\r\n")
            part.writer(channel)
            channel.writeStringUtf8("\r\n")
        }
        channel.writeStringUtf8("--$boundary--\r\n")
    }

    data class Part(val name: String, val filename: String?, val headers: Headers = Headers.Empty, val writer: suspend ByteWriteChannel.() -> Unit)

    class Builder {
        private val parts = arrayListOf<Part>()

        private fun add(part: Part) {
            parts += part
        }

        fun add(name: String, filename: String, contentType: ContentType, headers: Headers = Headers.Empty, writer: suspend ByteWriteChannel.() -> Unit) {
            add(Part(name, filename, headers + headersOf(HttpHeaders.ContentType, contentType.toString()), writer))
        }

        fun add(name: String, filename: String, contentType: ContentType, data: ByteArray, headers: Headers = Headers.Empty) {
            add(name, filename, contentType, headers) {
                writeFully(data)
            }
        }

        fun add(name: String, text: String, contentType: ContentType? = null) {
            add(Part(name, null, headersOf(HttpHeaders.ContentType, contentType.toString())) { writeStringUtf8(text) })
        }

        fun add(vararg pairs: Pair<String, Any?>) {
            for ((first, second) in pairs) {
                add(first, second?.toString() ?: continue)
            }
        }

        internal fun build(): MultiPartContent {
            return MultiPartContent(parts.toList())
        }
    }
}

class JsonTextContent(private val json: JsonObject): OutgoingContent.WriteChannelContent() {
    override val contentType = ContentType.Application.Json.withCharset(Charsets.UTF_8)

    override suspend fun writeTo(channel: ByteWriteChannel) {
        channel.writeStringUtf8(json.toJsonString())
    }

    class Builder {
        private val updates = mutableListOf<JsonBuilder.() -> Unit>()

        fun add(key: String, value: Any?) {
            updates += {
                key to value.asJsonElement()
            }
        }

        fun add(vararg pairs: Pair<String, Any?>) {
            for ((first, second) in pairs) {
                add(first, second)
            }
        }

        internal fun build(): JsonTextContent {
            return JsonTextContent(json {
                for (update in updates) {
                    update()
                }
            })
        }
    }
}

internal operator fun Headers.plus(other: Headers): Headers {
    return when {
        isEmpty() -> other
        other.isEmpty() -> this
        else -> Headers.build {
            appendAll(this@plus)
            appendAll(other)
        }
    }
}
