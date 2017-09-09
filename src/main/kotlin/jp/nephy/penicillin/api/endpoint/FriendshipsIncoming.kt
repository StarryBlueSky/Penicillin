package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class FriendshipsIncomingModel(val json: JsonElement) {
    val ids by json.byNullableArray // []
    val nextCursor by json.byNullableInt("next_cursor") // 0
    val nextCursorStr by json.byNullableString("next_cursor_str") // "0"
    val previousCursor by json.byNullableInt("previous_cursor") // 0
    val previousCursorStr by json.byNullableString("previous_cursor_str") // "0"
}

class FriendshipsIncoming(oauth: OAuthRequestHandler) : AbsOAuthGet<FriendshipsIncomingModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/friendships/incoming.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
