package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel


class Relationship(override val json: JsonObject): JsonModel {
    val source by json.byModel<RelationshipSource>()
    val target by json.byModel<RelationshipTarget>()
}
