package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.longList

data class FriendshipsNoRetweetsIds(override val json: JsonObject): PenicillinModel {
    val ids by longList
}
