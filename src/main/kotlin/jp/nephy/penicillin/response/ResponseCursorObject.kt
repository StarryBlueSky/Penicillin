package jp.nephy.penicillin.response

import jp.nephy.penicillin.PenicillinRequest
import jp.nephy.penicillin.model.Cursor
import okhttp3.Request
import okhttp3.Response

@Suppress("UNUSED")
class ResponseCursorObject<T>(private val klass: Class<T>, val result: T, content: String, val prevRequest: PenicillinRequest, request: Request, response: Response): AbsRESTResponse(content, request, response) {
    fun next(): ResponseCursorObject<T> = getByCursor((result as Cursor).nextCursor)
    fun previous(): ResponseCursorObject<T> = getByCursor((result as Cursor).previousCursor)

    fun untilLast(): List<ResponseCursorObject<T>> {
        val results = mutableListOf<ResponseCursorObject<T>>()

        var cursor = (result as Cursor).nextCursor
        while (true) {
            val response = getByCursor(cursor)
            cursor = (response.result as Cursor).nextCursor
            if (cursor == 0L) {
                return results
            }

            results.add(response)
        }
    }

    fun getByCursor(cursor: Long): ResponseCursorObject<T> {
        if (cursor == 0L) {
            throw IllegalStateException("Cursor is 0.")
        }

        return when (request.method()) {
            "GET" -> {
                prevRequest.param("cursor" to cursor)
                prevRequest.get().getResponseCursorObjectByClass(klass)
            }
            "POST" -> {
                prevRequest.dataAsForm("cursor" to cursor)
                prevRequest.post().getResponseCursorObjectByClass(klass)
            }
            else -> throw UnsupportedOperationException("HTTP Method `${request.method()}` is not supported.")
        }
    }
}
