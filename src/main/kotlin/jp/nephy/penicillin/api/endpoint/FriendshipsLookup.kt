package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class FriendshipsLookupModel(val json: JsonElement) {
    val connections by json.byNullableArray // ["none"]
    val id by json.byNullableInt // 7080152
    val idStr by json.byNullableString("id_str") // "7080152"
    val name by json.byNullableString // "Twitter Japan"
    val screenName by json.byNullableString("screen_name") // "TwitterJP"
}
// List
class FriendshipsLookup(oauth: OAuthRequestHandler) : AbsOAuthGet<FriendshipsLookupModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/friendships/lookup.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
