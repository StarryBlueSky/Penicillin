package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class UserMetadata(override val json: JsonObject): PenicillinModel {
    val resultType by string("result_type")
}
