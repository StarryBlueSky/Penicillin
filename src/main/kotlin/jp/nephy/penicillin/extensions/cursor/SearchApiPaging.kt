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

import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.response.JsonObjectResponse
import jp.nephy.penicillin.extensions.editRequest
import jp.nephy.penicillin.models.Search

/*
    Next operations
 */

val JsonObjectResponse<Search>.hasNext: Boolean
    get() = !result.searchMetadata.nextResults.isNullOrBlank()

private val NextQueryNotFound = LocalizedString("次の検索結果はありません。", "It is the last result of search.")

val JsonObjectResponse<Search>.next: JsonObjectApiAction<Search>
    get() {
        if (!hasNext) {
            throw PenicillinLocalizedException(NextQueryNotFound)
        }

        result.searchMetadata.nextResults!!.removePrefix("?").split("&").map {
            it.split("=", limit = 2)
        }.forEach { (k, v) ->
            action.editRequest {
                parameter(k to v)
            }
        }

        return action.request.jsonObject()
    }

/*
    Refresh operation
 */

val JsonObjectResponse<Search>.refreshable: Boolean
    get() = !result.searchMetadata.refreshUrl.isNullOrBlank()

private val RefreshUrlNotFound = LocalizedString("更新できる検索結果はありません。", "It is not refreshable search endpoint.")

val JsonObjectResponse<Search>.refresh: JsonObjectApiAction<Search>
    get() {
        if (!refreshable) {
            throw PenicillinLocalizedException(RefreshUrlNotFound)
        }

        result.searchMetadata.refreshUrl!!.removePrefix("?").split("&").map {
            it.split("=", limit = 2)
        }.forEach { (k, v) ->
            action.editRequest {
                parameter(k to v)
            }
        }

        return action.request.jsonObject()
    }
