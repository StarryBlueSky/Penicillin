package jp.nephy.penicillin.core.exceptions

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse

@Suppress("UNUSED")
open class PenicillinException(override val message: String, val error: TwitterErrorMessage? = null, val request: HttpRequest? = null, val response: HttpResponse? = null,
    override val cause: Throwable? = null): Exception()
