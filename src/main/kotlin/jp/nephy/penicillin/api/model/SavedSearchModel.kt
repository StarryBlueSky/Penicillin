package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byNullableLong
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement

class SavedSearchModel(val json: JsonElement) {
    val createdAt by json.byNullableString("created_at") // "Sat Sep 09 08:10:41 +0000 2017"
    val id by json.byNullableLong // 906429641543475200
    val idStr by json.byNullableString("id_str") // "906429641543475200"
    val name by json.byNullableString // "あ"
    val position by json.byNullableString // null
    val query by json.byNullableString // "あ"
}
