package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class TrendsAvailableModel(val json: JsonElement) {
    val country by json.byNullableString // ""
    val countryCode by json.byNullableString // null
    val name by json.byNullableString // "Worldwide"
    val parentid by json.byNullableInt // 0
    val placeType by json.byNullableObject // {"code": 19, "name": "Supername"}
    val url by json.byNullableString // "http://where.yahooapis.com/v1/place/1"
    val woeid by json.byNullableInt // 1
}
//list
class TrendsAvailable(oauth: OAuthRequestHandler) : AbsOAuthGet<TrendsAvailableModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/trends/available.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
