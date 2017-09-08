package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.byNullableArray
import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.api.ResponseObject
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class BlocksListModel(val json: JsonElement) {
    val nextCursor by json.byNullableInt("next_cursor") // 0
    val nextCursorStr by json.byNullableString("next_cursor_str") // "0"
    val previousCursor by json.byNullableInt("previous_cursor") // 0
    val previousCursorStr by json.byNullableString("previous_cursor_str") // "0"
    val users by json.byNullableArray // []
}

class BlocksList(oauth: OAuthRequestHandler): AbsOAuthGet<ResponseObject<BlocksListModel>>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/blocks/list.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val requestsPer15mins = 15
    override val defaultParameter = Parameter().apply {
        put("include_entities", "true")
        put("skip_status", "true")
        put("cursor", null)
    }
}
