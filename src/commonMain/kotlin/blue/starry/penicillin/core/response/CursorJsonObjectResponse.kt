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

package blue.starry.penicillin.core.response

import blue.starry.jsonkt.JsonObject
import blue.starry.penicillin.core.request.action.ApiAction
import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.models.cursor.PenicillinCursorModel
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * The [ApiResponse] that provides parsed json object with json model. This class supports cursor api operation.
 */
public data class CursorJsonObjectResponse<M: PenicillinCursorModel<T>, T: Any>(
    override val client: ApiClient,

    /**
     * Result of response.
     */
    public val result: M,

    override val request: HttpRequest,
    override val response: HttpResponse,
    override val content: String,
    override val action: ApiAction<CursorJsonObjectResponse<M, T>>
): ApiResponse<CursorJsonObjectResponse<M, T>>, JsonResponse<M, JsonObject>, CompletedResponse {
    override val json: JsonObject
        get() = result.json
}
