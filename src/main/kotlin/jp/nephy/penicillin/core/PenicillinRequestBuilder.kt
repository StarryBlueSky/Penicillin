package jp.nephy.penicillin.core

import com.google.gson.JsonObject
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.client.utils.EmptyContent
import io.ktor.http.*
import io.ktor.http.content.OutgoingContent
import io.ktor.util.appendAll
import io.ktor.util.flattenEntries
import jp.nephy.jsonkt.jsonObject
import jp.nephy.jsonkt.set
import jp.nephy.jsonkt.toJsonString
import jp.nephy.penicillin.core.auth.AuthorizationHandler
import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.core.emulation.Twitter4iPhone
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.endpoints.EndpointHost
import jp.nephy.penicillin.endpoints.PrivateEndpoint
import kotlinx.coroutines.experimental.io.ByteWriteChannel
import kotlinx.coroutines.experimental.io.writeFully
import kotlinx.coroutines.experimental.io.writeStringUtf8
import mu.KotlinLogging
import java.util.*

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
        for (pair in pairs) {
            header(pair.first, pair.second, emulationMode)
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
        for (pair in pairs) {
            parameter(pair.first, pair.second, emulationMode)
        }
    }

    private var body: Any = EmptyContent
    fun body(builder: RequestBodyBuilder.() -> Unit) {
        body = RequestBodyBuilder(session.option.emulationMode).apply(builder).build()
    }

    private lateinit var urlCache: String
    val url: String
        get() = urlCache

    internal fun finalize(): (HttpRequestBuilder) -> Unit {
        header(AuthorizationHandler.urlHeader, URLBuilder(protocol = protocol, host = host.value, port = protocol.defaultPort, encodedPath = path).buildString())
        header(AuthorizationHandler.authorizationTypeHeader, authorizationType.name)
        header(AuthorizationHandler.oauthCallbackUrlHeader, oauthCallbackUrl)

        if (session.option.emulationMode == EmulationMode.TwitterForiPhone) {
            if (authorizationType == AuthorizationType.OAuth1a) {
                header(*Twitter4iPhone.headers)
                header("kdt", session.credentials.knownDeviceToken)
                header("X-B3-TraceId", Twitter4iPhone.b3TraceId())
            }
        }

        return {
            it.method = httpMethod
            it.url(url)
            it.headers.appendAll(headers)
            it.body = body
        }
    }

    private fun checkEmulation() {
        val trace = Thread.currentThread().stackTrace.find {
            it.className.startsWith("jp.nephy.penicillin.endpoints")
        } ?: return
        val javaClass = javaClass.classLoader.loadClass(trace.className)
        val method = javaClass.methods.find { it.name == trace.methodName } ?: return

        logger.trace { "Endpoint: ${javaClass.simpleName}#${method.name}" }

        if (method.isAnnotationPresent(PrivateEndpoint::class.java) && session.option.emulationMode == EmulationMode.None) {
            throw PenicillinLocalizedException(LocalizedString.PrivateEndpointRequiresOfficialClientEmulation)
        }
    }

    internal fun build(): PenicillinRequest {
        checkEmulation()

        urlCache = URLBuilder(protocol = protocol, host = host.value, port = protocol.defaultPort, encodedPath = path, parameters = parameters).buildString()

        return PenicillinRequest(session, this)
    }
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
            for (pair in pairs) {
                add(pair.first, pair.second, emulationMode)
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
            for (pair in pairs) {
                add(pair.first, pair.second?.toString() ?: continue)
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
        private val json = jsonObject()

        fun add(key: String, value: Any?) {
            json[key] = value
        }

        fun add(vararg pairs: Pair<String, Any?>) {
            for (pair in pairs) {
                add(pair.first, pair.second)
            }
        }

        internal fun build(): JsonTextContent {
            return JsonTextContent(json)
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
