package jp.nephy.penicillin.request

import jp.nephy.jsonkt.JsonModel
import jp.nephy.penicillin.LocalizedString
import jp.nephy.penicillin.PenicillinLocalizedException
import jp.nephy.penicillin.model.Cursor
import jp.nephy.penicillin.util.AccessLevel
import jp.nephy.penicillin.util.RateLimit
import okhttp3.Headers
import okhttp3.Request
import okhttp3.Response

interface Result {
    val request: Request
    val response: Response

    val responseHeaders: Headers
        get() = response.headers()
    val responseTimeMs: Int?
        get() = responseHeaders["x-response-time"]?.toIntOrNull()

    val rateLimit: RateLimit
        get() = RateLimit(responseHeaders)
    val accessLevel: AccessLevel
        get() = AccessLevel.getLevel(responseHeaders)
}
interface ModelResult: Result {
    val model: Class<*>
}

class ObjectResult<T: JsonModel>(val result: T, override val model: Class<T>, override val request: Request, override val response: Response): ModelResult
class ListResult<T: JsonModel>(val result: List<T>, override val model: Class<T>, override val request: Request, override val response: Response): ModelResult
class TextResult(val result: String, override val request: Request, override val response: Response): Result
class CursorObjectResult<T: Cursor>(val result: T, override val model: Class<T>, private val requestBuilder: PenicillinRequestBuilder, override val request: Request, override val response: Response): ModelResult {
    fun next() = byCursor(result.nextCursor)
    fun previous() = byCursor(result.previousCursor)

    fun untilLast(): List<CursorObjectResult<T>> {
        val results = mutableListOf(this)

        var cursor = result.nextCursor
        while (true) {
            if (cursor == 0L) {
                return results
            }

            val response = byCursor(cursor).complete()
            results.add(response)
            cursor = response.result.nextCursor
        }
    }

    fun byCursor(cursor: Long): CursorObjectAction<T> {
        if (cursor == 0L) {
            throw PenicillinLocalizedException(LocalizedString.CursorIsZero)
        }

        return CursorObjectAction(model, requestBuilder)
    }
}
class StreamResult(override val request: Request, override val response: Response): Result
