package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

@Suppress("UNUSED")
class Relationship(val json: JsonElement) {
    val source by json.byModel<RelationshipSource>()
    val target by json.byModel<RelationshipTarget>()
}
