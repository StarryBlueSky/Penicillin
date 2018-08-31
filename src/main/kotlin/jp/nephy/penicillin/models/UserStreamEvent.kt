package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLambda
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.special.CreatedAt

abstract class UserStreamEvent(json: JsonObject): PenicillinModel {
    val event by json.byString
    val source by json.byModel<User>()
    val target by json.byModel<User>()
    val createdAt by json.byLambda("created_at") { CreatedAt(string) }
}
