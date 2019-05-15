package jp.nephy.penicillin.core.exceptions

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import jp.nephy.penicillin.core.i18n.LocalizedString

private val apiErrorString = LocalizedString(
    "%s (%d): %s (%s)"
)

/**
 * The [PenicillinException] class which is thrown when Twitter API error occurs.
 */
class PenicillinTwitterApiException(
    /**
     * Pre-defined [TwitterApiError].
     */
    val error: TwitterApiError,

    /**
     * Executed [HttpRequest].
     */
    override val request: HttpRequest,

    /**
     * Executed [HttpResponse].
     */
    override val response: HttpResponse
): PenicillinException(apiErrorString, null, request, response, error.title, error.code, error.description, request.url)
