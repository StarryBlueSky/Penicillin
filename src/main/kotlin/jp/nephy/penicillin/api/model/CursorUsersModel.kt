package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byNullableArray
import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableLong
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement

class CursorUsersModel(val json: JsonElement) {
    val nextCursor by json.byNullableLong("next_cursor") // 1570506826793099612
    val nextCursorStr by json.byNullableString("next_cursor_str") // "1570506826793099612"
    val previousCursor by json.byNullableInt("previous_cursor") // 0
    val previousCursorStr by json.byNullableString("previous_cursor_str") // "0"
    val users by json.byNullableArray // [UserModel]
}
