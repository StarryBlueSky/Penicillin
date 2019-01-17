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
import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.CursorJsonObjectResponse
import jp.nephy.penicillin.models.PenicillinCursorModel
import kotlinx.coroutines.CancellationException
import kotlin.reflect.KClass

data class CursorJsonObjectApiAction<M: PenicillinCursorModel>(override val request: ApiRequest, override val model: KClass<M>): JsonRequest<M>, ApiAction<CursorJsonObjectResponse<M>> {
    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): CursorJsonObjectResponse<M> {
        val (request, response) = execute(request)
        val content = response.readTextSafe()
        checkError(request, response, content)

        val json = content?.toJsonObjectSafe() ?: throw PenicillinLocalizedException(
            LocalizedString.JsonParsingFailed,
            request,
            response,
            null,
            content
        )
        val result = json.parseSafe(model, content) ?: throw PenicillinLocalizedException(
            LocalizedString.JsonModelCastFailed, request, response, null, model.simpleName, content
        )

        return CursorJsonObjectResponse(model, result, request, response, content, this)
    }
}
