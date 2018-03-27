package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byString


class Privacy(override val json: JsonObject): JsonModel {
    val privacy by json.byString
}
