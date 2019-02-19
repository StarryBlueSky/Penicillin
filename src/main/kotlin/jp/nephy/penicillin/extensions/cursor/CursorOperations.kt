/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions.cursor

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.action.CursorJsonObjectApiAction
import jp.nephy.penicillin.core.response.CursorJsonObjectResponse
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.extensions.complete
import jp.nephy.penicillin.extensions.editRequest
import jp.nephy.penicillin.models.cursor.PenicillinCursorModel

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

fun <M: PenicillinCursorModel> CursorJsonObjectResponse<M>.byCursor(cursor: Long, vararg options: Option): CursorJsonObjectApiAction<M> {
    if (cursor == 0L) {
        throw PenicillinLocalizedException(LocalizedString.CursorIsZero, request, response)
    }

    action.editRequest {
        parameter("cursor" to cursor, *options)
    }

    return CursorJsonObjectApiAction(client, action.request, model)
}

@Throws(PenicillinException::class)
fun <M: PenicillinCursorModel> CursorJsonObjectApiAction<M>.untilLast(vararg options: Option): Sequence<CursorJsonObjectResponse<M>> {
    return sequence {
        val first = complete()
        yield(first)

        var cursor = first.nextCursor
        while (cursor != 0L) {
            val result = first.byCursor(cursor, *options).complete()

            yield(result)
            cursor = result.nextCursor
        }
    }
}

@Throws(PenicillinException::class)
fun <M: PenicillinCursorModel> CursorJsonObjectResponse<M>.untilLast(vararg options: Option): Sequence<CursorJsonObjectResponse<M>> {
    return sequence {
        yield(this@untilLast)

        if (hasNext) {
            yieldAll(next.untilLast(*options))
        }
    }
}
