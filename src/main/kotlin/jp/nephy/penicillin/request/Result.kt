package jp.nephy.penicillin.request

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.jsonArray
import jp.nephy.penicillin.LocalizedString
import jp.nephy.penicillin.PenicillinLocalizedException
import jp.nephy.penicillin.model.Cursor
import jp.nephy.penicillin.model.CursorIds
import jp.nephy.penicillin.model.CursorLists
import jp.nephy.penicillin.model.CursorUsers
import jp.nephy.penicillin.model.special.AccessLevel
import jp.nephy.penicillin.model.special.RateLimit
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

class ObjectResult<T: JsonModel>(val result: T, override val model: Class<T>, override val request: Request, override val response: Response): ModelResult {
    val json: JsonObject
        get() = result.json
}
class ListResult<T: JsonModel>(val result: List<T>, override val model: Class<T>, override val request: Request, override val response: Response): ModelResult {
    val json: JsonArray
        get() = jsonArray(*result.map { it.json }.toTypedArray())
}
class TextResult(val result: String, override val request: Request, override val response: Response): Result
class CursorObjectResult<T: Cursor>(val result: T, override val model: Class<T>, private val requestBuilder: PenicillinRequestBuilder, override val request: Request, override val response: Response): ModelResult {
    fun next() = byCursor(result.nextCursor)
    fun previous() = byCursor(result.previousCursor)

    fun untilLast(): List<CursorObjectResult<T>> {
        val results = mutableListOf(this)

        var cursor = result.nextCursor
        while (cursor != 0L) {
            val response = try {
                byCursor(cursor).complete()
            } catch (e: Exception) {
                break
            }

            results.add(response)
            cursor = response.result.nextCursor
        }
        return results
    }

    fun byCursor(cursor: Long): CursorObjectAction<T> {
        if (cursor == 0L) {
            throw PenicillinLocalizedException(LocalizedString.CursorIsZero)
        }

        return CursorObjectAction(model, requestBuilder.apply { query("cursor" to cursor) })
    }
}
class StreamResult(override val request: Request, override val response: Response): Result

val List<CursorObjectResult<CursorIds>>.allIds
    get() = map { it.result.ids }.flatten()
val List<CursorObjectResult<CursorLists>>.allLists
    get() = map { it.result.lists }.flatten()
val List<CursorObjectResult<CursorUsers>>.allUsers
    get() = map { it.result.users }.flatten()
