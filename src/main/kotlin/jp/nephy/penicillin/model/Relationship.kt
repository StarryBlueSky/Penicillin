package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byRelationshipSource
import jp.nephy.penicillin.converter.byRelationshipTarget

class Relationship(val json: JsonElement) {
    val source by json.byRelationshipSource
    val target by json.byRelationshipTarget
}
