package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byModel


class Contributees(override val json: JsonObject): JsonModel {
    val admin by json.byBool
    val user by json.byModel<User>()
}
