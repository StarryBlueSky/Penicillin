package jp.nephy.penicillin.api.result

import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement

class UserSuggestionModel(val json: JsonElement) {
    val name by json.byNullableString // "Twitter"
    val size by json.byNullableInt // 13
    val slug by json.byNullableString // "twitter"
}
