package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class FollowersListModel(val json: JsonElement) {
    val nextCursor by json.byNullableLong("next_cursor") // 1570506826793099612
    val nextCursorStr by json.byNullableString("next_cursor_str") // "1570506826793099612"
    val previousCursor by json.byNullableInt("previous_cursor") // 0
    val previousCursorStr by json.byNullableString("previous_cursor_str") // "0"
    val users by json.byNullableArray // [UserModel]
}

class FollowersList(oauth: OAuthRequestHandler) : AbsOAuthGet<FollowersListModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/followers/list.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
