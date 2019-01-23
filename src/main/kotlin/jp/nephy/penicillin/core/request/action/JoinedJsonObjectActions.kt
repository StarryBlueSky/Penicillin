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

package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.JsonObjectResponse
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.CancellationException

typealias JoinedJsonObjectActionCallback<M> = (results: List<List<JsonObjectResponse<*>>>) -> JsonObjectApiAction<M>

// TODO
class JoinedJsonObjectActions<M: PenicillinModel, T: PenicillinModel>(
    override val client: PenicillinClient,
    private val actions: List<MultipleJsonObjectActions<M>>,
    private val finalizer: JoinedJsonObjectActionCallback<T>
): ApiAction<JsonObjectResponse<T>> {
    override val request: ApiRequest
        get() = actions.last().first.request

    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): JsonObjectResponse<T> {
        return finalizer(actions.map { it.await() }).await()
    }
}
