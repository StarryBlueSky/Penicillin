package jp.nephy.penicillin.core.request.body

import io.ktor.http.*
import io.ktor.http.content.OutgoingContent
import jp.nephy.penicillin.core.emulation.EmulationMode
import kotlinx.coroutines.io.ByteWriteChannel
import kotlinx.coroutines.io.writeStringUtf8

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
