@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions.cursor

import jp.nephy.penicillin.core.exceptions.PenicillinLocalizedException
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.response.JsonObjectResponse
import jp.nephy.penicillin.models.Search

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
            action.request.builder.parameter(k to v)
        }

        return action.request.jsonObject()
    }
