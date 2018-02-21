package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class AdditionalMediaInfo(override val json: JsonObject): JsonModel {
    val title by json.byString
    val description by json.byString
    val embeddable by json.byBool
}
