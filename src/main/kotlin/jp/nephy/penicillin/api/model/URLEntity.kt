package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byIntArray
import jp.nephy.penicillin.api.byURL

class URLEntity(val json: JsonElement) {
    val url by json.byURL
    val expandedUrl by json.byURL("expanded_url")
    val displayUrl by json.byURL("display_url")
    val indices by json.byIntArray
}
