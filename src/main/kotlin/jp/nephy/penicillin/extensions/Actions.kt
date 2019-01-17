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

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.request.action.JoinedJsonObjectActionCallback
import jp.nephy.penicillin.core.request.action.JoinedJsonObjectActions
import jp.nephy.penicillin.core.request.action.PenicillinMultipleJsonObjectActions
import jp.nephy.penicillin.core.response.JsonObjectResponse
import jp.nephy.penicillin.models.PenicillinModel

inline fun <reified M: PenicillinModel> List<JsonObjectResponse<*>>.filter(): List<JsonObjectResponse<M>> {
    @Suppress("UNCHECKED_CAST") return filter { it.model == M::class }.map { it as JsonObjectResponse<M> }
}

fun <M: PenicillinModel, T: PenicillinModel> List<PenicillinMultipleJsonObjectActions<M>>.join(finalizer: JoinedJsonObjectActionCallback<T>): JoinedJsonObjectActions<M, T> {
    return JoinedJsonObjectActions(this, finalizer)
}
