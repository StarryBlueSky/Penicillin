package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byIntList
import jp.nephy.jsonkt.byString


data class URLEntity(override val json: JsonObject): PenicillinModel {
    val url by json.byString
    val expandedUrl by json.byString("expanded_url")
    val displayUrl by json.byString("display_url")
    val indices by json.byIntList
}
