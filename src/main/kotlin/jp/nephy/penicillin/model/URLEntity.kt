package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byIntList
import jp.nephy.jsonkt.byString


class URLEntity(override val json: JsonObject): JsonModel {
    val url by json.byString
    val expandedUrl by json.byString("expanded_url")
    val displayUrl by json.byString("display_url")
    val indices by json.byIntList
}
