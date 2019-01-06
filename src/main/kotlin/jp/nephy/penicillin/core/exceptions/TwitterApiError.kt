package jp.nephy.penicillin.core.exceptions

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import jp.nephy.penicillin.core.i18n.LocalizedString

class TwitterApiError(code: Int, title: String, content: String, request: HttpRequest, response: HttpResponse): Exception() {
    init {
        val message = TwitterErrorMessage.values().find { it.code == code }
            ?: throw PenicillinLocalizedException(LocalizedString.UnknownApiError, request, response, null, code, title, content)

        throw PenicillinException("${message.title} (${message.code}): ${message.description} (${request.url})", message, request, response, this)
    }
}
