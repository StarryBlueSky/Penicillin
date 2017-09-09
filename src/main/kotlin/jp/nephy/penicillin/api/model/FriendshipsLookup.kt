package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byNullableArray
import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement

class FriendshipsLookup(val json: JsonElement) {
    val connections by json.byNullableArray // ["none"]
    val id by json.byNullableInt // 7080152
    val idStr by json.byNullableString("id_str") // "7080152"
    val name by json.byNullableString // "Twitter Japan"
    val screenName by json.byNullableString("screen_name") // "TwitterJP"
}
