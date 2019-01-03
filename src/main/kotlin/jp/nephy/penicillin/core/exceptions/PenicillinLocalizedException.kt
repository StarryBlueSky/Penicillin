package jp.nephy.penicillin.core.exceptions

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import jp.nephy.penicillin.core.i18n.LocalizedString

class PenicillinLocalizedException(localizedString: LocalizedString, request: HttpRequest? = null, response: HttpResponse? = null, cause: Throwable? = null, vararg args: Any?):
    PenicillinException(localizedString.format(*args), request = request, response = response, cause = cause)
