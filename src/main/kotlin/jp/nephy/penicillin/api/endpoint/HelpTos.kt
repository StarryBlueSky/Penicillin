package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class HelpTosModel(val json: JsonElement) {
    val tos by json.byString
}

class HelpTos(oauth: OAuthRequestHandler): AbsOAuthGet<HelpTosModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/help/tos.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
