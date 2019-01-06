@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

object Friendships {
    data class Show(override val json: JsonObject): PenicillinModel {
        val relationship by model<Relationship>()
    }

    data class Lookup(override val json: JsonObject): PenicillinModel {
        val connections by stringList
        val id by long
        val idStr by string("id_str")
        val name by string
        val screenName by string("screen_name")
    }

    data class NoRetweetsIds(override val json: JsonObject): PenicillinModel {
        val ids by longList
    }
}
