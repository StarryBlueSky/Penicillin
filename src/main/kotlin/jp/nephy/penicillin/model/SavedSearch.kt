package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.misc.CreatedAt

class SavedSearch(val json: JsonElement) {
    val createdAt by json.byConverter<String, CreatedAt>("created_at")
    val id by json.byLong
    val idStr by json.byString("id_str")
    val name by json.byString
    val position by json.byString
    val query by json.byString
}
