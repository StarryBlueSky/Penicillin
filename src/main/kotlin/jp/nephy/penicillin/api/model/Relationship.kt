package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byRelationshipSource
import jp.nephy.penicillin.api.byRelationshipTarget

class Relationship(val json: JsonElement) {
    val source by json.byRelationshipSource
    val target by json.byRelationshipTarget
}
