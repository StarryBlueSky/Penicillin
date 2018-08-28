package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString

class Language(override val json: JsonObject): PenicillinModel {
    val code by json.byString
    val status by json.byString
    val name by json.byString
}
