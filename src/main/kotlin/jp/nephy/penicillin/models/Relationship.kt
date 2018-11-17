@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Relationship(override val json: JsonObject): PenicillinModel {
    val source by model<RelationshipSource>()
    val target by model<RelationshipTarget>()
}
