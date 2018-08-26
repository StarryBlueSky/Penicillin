package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byString


class AdditionalMediaInfo(override val json: JsonObject): PenicillinModel {
    val title by json.byString
    val description by json.byString
    val embeddable by json.byBool
}
