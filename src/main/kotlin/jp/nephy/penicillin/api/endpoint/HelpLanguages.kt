package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class HelpLanguagesModel(val json: JsonElement) {
    val code by json.byString // "fr"
    val status by json.byString // "production"
    val name by json.byString // "French"
}

class HelpLanguages(oauth: OAuthRequestHandler): AbsOAuthGet<HelpLanguagesModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/help/languages.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val requestsPer15mins = 15
    override val defaultParameter = Parameter()
}
