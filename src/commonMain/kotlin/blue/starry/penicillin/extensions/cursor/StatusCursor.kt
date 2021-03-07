/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

@file:Suppress("Unused")

package blue.starry.penicillin.extensions.cursor

import blue.starry.penicillin.core.request.action.JsonArrayApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.extensions.awaitRefresh
import blue.starry.penicillin.extensions.edit
import blue.starry.penicillin.extensions.isExceeded
import blue.starry.penicillin.extensions.rateLimit
import blue.starry.penicillin.models.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

/**
 * Retrieves all the statuses until last from current action.
 * This operation is sequence.
 *
 * @param count max statuses count.
 * @param options Optional. Custom parameters of this request.
 */
public fun JsonArrayApiAction<Status>.untilLast(count: Int = 200, vararg options: Option): Flow<Status> = flow {
    var maxId: Long? = null

    while (true) {
        edit {
            parameters(
                "count" to count,
                "max_id" to maxId,
                *options
            )
        }

        val response = invoke()
        val statuses = response.filter { it.id != maxId }
        if (statuses.isEmpty()) {
            break
        }

        emitAll(statuses.asFlow())

        maxId = statuses.last().id

        val rateLimit = response.rateLimit ?: continue
        if (rateLimit.isExceeded) {
            rateLimit.awaitRefresh()
        }
    }
}
