@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions.cursor

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.action.CursorJsonObjectApiAction
import jp.nephy.penicillin.core.response.CursorJsonObjectResponse
import jp.nephy.penicillin.extensions.complete
import jp.nephy.penicillin.models.PenicillinCursorModel

/*
    Next operations
 */

val CursorJsonObjectResponse<*>.nextCursor: Long
    get() = result.nextCursor
val CursorJsonObjectResponse<*>.hasNext: Boolean
    get() = nextCursor > 0
val <M: PenicillinCursorModel> CursorJsonObjectResponse<M>.next: CursorJsonObjectApiAction<M>
    get() = byCursor(nextCursor)

/*
    Previous operations
 */

val CursorJsonObjectResponse<*>.previousCursor: Long
    get() = result.previousCursor
val CursorJsonObjectResponse<*>.hasPrevious: Boolean
    get() = previousCursor > 0
val <M: PenicillinCursorModel> CursorJsonObjectResponse<M>.previous: CursorJsonObjectApiAction<M>
    get() = byCursor(previousCursor)

/*
    Paging
 */

fun <M: PenicillinCursorModel> CursorJsonObjectResponse<M>.byCursor(cursor: Long): CursorJsonObjectApiAction<M> {
    if (cursor == 0L) {
        throw PenicillinLocalizedException(LocalizedString.CursorIsZero, request, response)
    }

    action.request.builder.parameter("cursor" to cursor)

    return CursorJsonObjectApiAction(action.request, model)
}

@Throws(PenicillinException::class)
fun <M: PenicillinCursorModel> CursorJsonObjectApiAction<M>.untilLast(): Sequence<CursorJsonObjectResponse<M>> {
    return sequence {
        val first = complete()
        yield(first)

        var cursor = first.nextCursor
        while (cursor != 0L) {
            val result = first.byCursor(cursor).complete()

            yield(result)
            cursor = result.nextCursor
        }
    }
}

@Throws(PenicillinException::class)
fun <M: PenicillinCursorModel> CursorJsonObjectResponse<M>.untilLast(): Sequence<CursorJsonObjectResponse<M>> {
    return sequence {
        yield(this@untilLast)

        if (hasNext) {
            yieldAll(next.untilLast())
        }
    }
}
