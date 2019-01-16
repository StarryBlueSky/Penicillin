package jp.nephy.penicillin.core.request.body

import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.http.withCharset
import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.asJsonElement
import jp.nephy.jsonkt.toJsonString
import kotlinx.coroutines.io.ByteWriteChannel
import kotlinx.coroutines.io.writeStringUtf8
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.json

class JsonTextContent(private val json: JsonObject): OutgoingContent.WriteChannelContent() {
    override val contentType = ContentType.Application.Json.withCharset(Charsets.UTF_8)

    override suspend fun writeTo(channel: ByteWriteChannel) {
        channel.writeStringUtf8(json.toJsonString())
    }

    class Builder {
        private val updates = mutableListOf<JsonObjectBuilder.() -> Unit>()

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
