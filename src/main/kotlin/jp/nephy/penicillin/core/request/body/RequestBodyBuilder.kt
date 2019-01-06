package jp.nephy.penicillin.core.request.body

import io.ktor.client.utils.EmptyContent
import jp.nephy.penicillin.core.emulation.EmulationMode

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
