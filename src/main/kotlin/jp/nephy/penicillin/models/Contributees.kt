package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byModel


class Contributees(override val json: JsonObject): PenicillinModel {
    val admin by json.byBool
    val user by json.byModel<User>()
}
