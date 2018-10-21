@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class UserStreamFriends(override val json: ImmutableJsonObject): PenicillinModel {
    val friends by nullableLongList()
    val friendsStr by nullableStringList
}
