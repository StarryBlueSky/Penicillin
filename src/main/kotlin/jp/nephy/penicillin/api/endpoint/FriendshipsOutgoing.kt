package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class FriendshipsOutgoingModel(val json: JsonElement) {
    val ids by json.byNullableArray // [2721589112, 2983080704, 3010140318, 2362824542]
    val nextCursor by json.byNullableInt("next_cursor") // 0
    val nextCursorStr by json.byNullableString("next_cursor_str") // "0"
    val previousCursor by json.byNullableInt("previous_cursor") // 0
    val previousCursorStr by json.byNullableString("previous_cursor_str") // "0"
}

class FriendshipsOutgoing(oauth: OAuthRequestHandler) : AbsOAuthGet<FriendshipsOutgoingModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/friendships/outgoing.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
