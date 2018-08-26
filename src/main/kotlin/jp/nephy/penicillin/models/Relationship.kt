package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel


class Relationship(override val json: JsonObject): PenicillinModel {
    val source by json.byModel<RelationshipSource>()
    val target by json.byModel<RelationshipTarget>()
}
