package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byIntList
import jp.nephy.jsonkt.byUrl


class URLEntity(override val json: JsonObject): JsonModel {
    val url by json.byUrl
    val expandedUrl by json.byUrl("expanded_url")
    val displayUrl by json.byUrl("display_url")
    val indices by json.byIntList
}
