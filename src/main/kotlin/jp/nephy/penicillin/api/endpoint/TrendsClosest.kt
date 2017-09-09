package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class TrendsClosestModel(val json: JsonElement) {
    val country by json.byNullableString // "Japan"
    val countryCode by json.byNullableString // "JP"
    val name by json.byNullableString // "Japan"
    val parentid by json.byNullableInt // 1
    val placeType by json.byNullableObject // {"code": 12, "name": "Country"}
    val url by json.byNullableString // "http://where.yahooapis.com/v1/place/23424856"
    val woeid by json.byNullableInt // 23424856
}
//list
class TrendsClosest(oauth: OAuthRequestHandler) : AbsOAuthGet<TrendsClosestModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/trends/closest.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
