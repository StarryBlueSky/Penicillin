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

package jp.nephy.penicillin.core.request

import jp.nephy.penicillin.core.request.action.*
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.streaming.handler.StreamHandler
import jp.nephy.penicillin.core.streaming.listener.StreamListener
import jp.nephy.penicillin.endpoints.PremiumSearchEnvironment
import jp.nephy.penicillin.models.PenicillinModel
import jp.nephy.penicillin.models.PremiumSearchModel
import jp.nephy.penicillin.models.cursor.PenicillinCursorModel

/**
 * Represents api request methods.
 */
class ApiRequest(
    /**
     * Current [ApiClient] instance.
     */
    val client: ApiClient,

    /**
     * Current request builder.
     */
    val builder: ApiRequestBuilder
) {
    /**
     * Creates [JsonObjectApiAction] from this request.
     *
     * @return New [JsonObjectApiAction] for [M].
     */
    inline fun <reified M: PenicillinModel> jsonObject(): JsonObjectApiAction<M> {
        return JsonObjectApiAction(client, this, M::class)
    }

    /**
     * Creates [JsonArrayApiAction] from this request.
     *
     * @return New [JsonArrayApiAction] for [M].
     */
    inline fun <reified M: PenicillinModel> jsonArray(): JsonArrayApiAction<M> {
        return JsonArrayApiAction(client, this, M::class)
    }

    /**
     * Creates [CursorJsonObjectApiAction] from this request.
     *
     * @return New [CursorJsonObjectApiAction] for [M].
     */
    inline fun <reified M: PenicillinCursorModel> cursorJsonObject(): CursorJsonObjectApiAction<M> {
        return CursorJsonObjectApiAction(client, this, M::class)
    }

    /**
     * Creates [PremiumSearchJsonObjectApiAction] from this request.
     *
     * @return New [PremiumSearchJsonObjectApiAction] for [M].
     */
    inline fun <reified M: PremiumSearchModel> premiumSearchJsonObject(environment: PremiumSearchEnvironment): PremiumSearchJsonObjectApiAction<M> {
        return PremiumSearchJsonObjectApiAction(client, this, M::class, environment)
    }

    /**
     * Creates [TextApiAction] from this request.
     *
     * @return New [TextApiAction].
     */
    fun text(): TextApiAction {
        return TextApiAction(client, this)
    }

    /**
     * Creates [EmptyApiAction] from this request.
     *
     * @return New [EmptyApiAction].
     */
    fun empty(): EmptyApiAction {
        return EmptyApiAction(client, this)
    }

    /**
     * Creates [StreamApiAction] from this request.
     *
     * @return New [StreamApiAction] for [L] and [H].
     */
    fun <L: StreamListener, H: StreamHandler<L>> stream(): StreamApiAction<L, H> {
        return StreamApiAction(client, this)
    }
}
