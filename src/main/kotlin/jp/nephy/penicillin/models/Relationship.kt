@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Relationship(override val json: ImmutableJsonObject): PenicillinModel {
    val source by model<RelationshipSource>()
    val target by model<RelationshipTarget>()
}
