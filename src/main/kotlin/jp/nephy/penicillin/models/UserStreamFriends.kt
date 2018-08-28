package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byNullableLongList
import jp.nephy.jsonkt.byNullableStringList

class UserStreamFriends(override val json: JsonObject): PenicillinModel {
    val friends by json.byNullableLongList()
    val friendsStr by json.byNullableStringList
}
