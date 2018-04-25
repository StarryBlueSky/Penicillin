package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.model.special.CreatedAt

open class UserStreamEvent(final override val json: JsonObject): JsonModel {
    val event by json.byString
    val source by json.byModel<User>()
    val target by json.byModel<User>()
    val createdAt by json.byLambda("created_at") { CreatedAt(string) }
}
