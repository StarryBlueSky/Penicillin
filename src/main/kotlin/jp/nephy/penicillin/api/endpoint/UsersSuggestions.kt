package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class UsersSuggestionsModel(val json: JsonElement) {
    val name by json.byNullableString // "Twitter"
    val size by json.byNullableInt // 13
    val slug by json.byNullableString // "twitter"
}
//list
class UsersSuggestions(oauth: OAuthRequestHandler) : AbsOAuthGet<UsersSuggestionsModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/users/suggestions.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
