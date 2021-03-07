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

package blue.starry.penicillin.core.request

import blue.starry.jsonkt.JsonObject
import blue.starry.penicillin.core.request.action.*
import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.core.streaming.handler.StreamHandler
import blue.starry.penicillin.core.streaming.listener.StreamListener
import blue.starry.penicillin.endpoints.PremiumSearchEnvironment
import blue.starry.penicillin.models.PenicillinModel
import blue.starry.penicillin.models.PremiumSearchModel
import blue.starry.penicillin.models.cursor.PenicillinCursorModel

/**
 * Represents api request methods.
 */
public class ApiRequest(
    /**
     * Current [ApiClient] instance.
     */
    public val client: ApiClient,

    /**
     * Current request builder.
     */
    public val builder: ApiRequestBuilder
) {
    /**
     * Creates [JsonObjectApiAction] from this request.
     *
     * @return New [JsonObjectApiAction] for [M].
     */
    public fun <M: PenicillinModel> jsonObject(converter: (JsonObject) -> M): JsonObjectApiAction<M> {
        return JsonObjectApiAction(client, this, converter)
    }

    /**
     * Creates [JsonArrayApiAction] from this request.
     *
     * @return New [JsonArrayApiAction] for [M].
     */
    public fun <M: PenicillinModel> jsonArray(converter: (JsonObject) -> M): JsonArrayApiAction<M> {
        return JsonArrayApiAction(client, this, converter)
    }

    /**
     * Creates [CursorJsonObjectApiAction] from this request.
     *
     * @return New [CursorJsonObjectApiAction] for [M].
     */
    public fun <M: PenicillinCursorModel<T>, T: Any> cursorJsonObject(converter: (JsonObject) -> M): CursorJsonObjectApiAction<M, T> {
        return CursorJsonObjectApiAction(client, this, converter)
    }

    /**
     * Creates [PremiumSearchJsonObjectApiAction] from this request.
     *
     * @return New [PremiumSearchJsonObjectApiAction] for [M].
     */
    public fun <M: PremiumSearchModel> premiumSearchJsonObject(environment: PremiumSearchEnvironment, converter: (JsonObject) -> M): PremiumSearchJsonObjectApiAction<M> {
        return PremiumSearchJsonObjectApiAction(client, this, converter, environment)
    }

    /**
     * Creates [TextApiAction] from this request.
     *
     * @return New [TextApiAction].
     */
    public fun text(): TextApiAction {
        return TextApiAction(client, this)
    }

    /**
     * Creates [EmptyApiAction] from this request.
     *
     * @return New [EmptyApiAction].
     */
    public fun empty(): EmptyApiAction {
        return EmptyApiAction(client, this)
    }

    /**
     * Creates [StreamApiAction] from this request.
     *
     * @return New [StreamApiAction] for [L] and [H].
     */
    public fun <L: StreamListener, H: StreamHandler<L>> stream(): StreamApiAction<L, H> {
        return StreamApiAction(client, this)
    }
}
