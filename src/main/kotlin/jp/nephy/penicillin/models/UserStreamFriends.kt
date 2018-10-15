package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class UserStreamFriends(override val json: JsonObject): PenicillinModel {
    val friends by nullableLongList()
    val friendsStr by nullableStringList
}
