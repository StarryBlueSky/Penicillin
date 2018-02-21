package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.misc.CreatedAt

@Suppress("UNUSED")
class SavedSearch(override val json: JsonObject): JsonModel {
    val createdAt by json.byLambda("created_at") { CreatedAt(string) }
    val id by json.byLong
    val idStr by json.byString("id_str")
    val name by json.byString
    val position by json.byString
    val query by json.byString
}
