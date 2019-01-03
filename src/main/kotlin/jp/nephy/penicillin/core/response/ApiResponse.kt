package jp.nephy.penicillin.core.response

import io.ktor.client.request.HttpRequest
import io.ktor.client.response.HttpResponse
import jp.nephy.penicillin.core.request.action.ApiAction
import java.io.Closeable

interface ApiResponse: Closeable {
    val request: HttpRequest
    val response: HttpResponse
    val action: ApiAction<*>

    override fun close() {
        response.close()
    }
}
