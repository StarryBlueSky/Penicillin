package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byString

class UserMetadata(override val json: JsonObject): JsonModel {
    val resultType by json.byString("result_type")
}
