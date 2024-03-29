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

package blue.starry.penicillin.core.request.action

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.parseObjectOrNull
import blue.starry.jsonkt.toJsonObjectOrNull
import blue.starry.penicillin.core.exceptions.PenicillinException
import blue.starry.penicillin.core.i18n.LocalizedString
import blue.starry.penicillin.core.request.ApiRequest
import blue.starry.penicillin.core.response.CursorJsonObjectResponse
import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.extensions.cursor.untilLast
import blue.starry.penicillin.models.cursor.PenicillinCursorModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.emitAll

/**
 * The [ApiAction] that provides parsed json object with json model. This class supports cursor api operation.
 */
@OptIn(FlowPreview::class)
public class CursorJsonObjectApiAction<M: PenicillinCursorModel<T>, T: Any>(
    override val client: ApiClient,
    override val request: ApiRequest,
    override val converter: (JsonObject) -> M
): JsonRequest<M>, ApiAction<CursorJsonObjectResponse<M, T>>, AbstractFlow<T>() {
    override suspend fun execute(): CursorJsonObjectResponse<M, T> {
        val (request, response) = finalize()

        val content = response.readTextOrNull()
        val json = content?.toJsonObjectOrNull() ?: throw PenicillinException(
            LocalizedString.JsonParsingFailed, null, request, response, content
        )

        checkError(request, response, content, json)

        val result = json.parseObjectOrNull(converter) ?: throw PenicillinException(
            LocalizedString.JsonModelCastFailed, null, request, response, content
        )

        return CursorJsonObjectResponse(client, result, request, response, content, this)
    }

    override suspend fun collectSafely(collector: FlowCollector<T>) {
        collector.emitAll(untilLast())
    }
}
