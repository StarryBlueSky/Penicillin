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

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.JsonObjectResponse
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.CancellationException
import kotlin.reflect.KClass

private typealias JsonObjectActionCallback<M> = suspend (results: MultipleJsonObjectActions.Results<M>) -> ApiAction<*>

// TODO
data class MultipleJsonObjectActions<M: PenicillinModel>(val first: JsonObjectApiAction<M>, private val requests: List<JsonObjectActionCallback<M>>): ApiAction<List<JsonObjectResponse<*>>> {
    override val request: ApiRequest
        get() = first.request

    class Builder<M: PenicillinModel>(private val first: () -> JsonObjectApiAction<M>) {
        private val requests = mutableListOf<JsonObjectActionCallback<M>>()
        fun request(callback: JsonObjectActionCallback<M>) = apply {
            requests.add(callback)
        }

        internal fun build(): MultipleJsonObjectActions<M> {
            return MultipleJsonObjectActions(first(), requests)
        }
    }

    class Results<M: PenicillinModel>(val first: JsonObjectResponse<M>, val responses: Map<KClass<out PenicillinModel>, List<JsonObjectResponse<*>>>)

    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): List<JsonObjectResponse<*>> {
        val first = first.await()
        val responses = mutableMapOf<KClass<out PenicillinModel>, MutableList<JsonObjectResponse<*>>>()

        return requests.mapNotNull { request ->
            @Suppress("UNCHECKED_CAST")
            val result = runCatching {
                request.invoke(Results(first, responses)).await() as? JsonObjectResponse<*>
            }.getOrNull() ?: return@mapNotNull null

            responses.getOrPut(result.model) { mutableListOf() } += result
            result
        }
    }

    operator fun plus(callback: JsonObjectActionCallback<M>): MultipleJsonObjectActions<M> {
        return Builder {
            first
        }.also { builder ->
            requests.forEach {
                builder.request(it)
            }
        }.request(callback).build()
    }
}
