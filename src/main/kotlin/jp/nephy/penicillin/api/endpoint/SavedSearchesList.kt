package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class SavedSearchesListModel(val json: JsonElement) {
    val createdAt by json.byNullableString("created_at") // "Sat Sep 09 08:10:41 +0000 2017"
    val id by json.byNullableLong // 906429641543475200
    val idStr by json.byNullableString("id_str") // "906429641543475200"
    val name by json.byNullableString // "あ"
    val position by json.byNullableString // null
    val query by json.byNullableString // "あ"
}

class SavedSearchesList(oauth: OAuthRequestHandler) : AbsOAuthGet<SavedSearchesListModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/saved_searches/list.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
