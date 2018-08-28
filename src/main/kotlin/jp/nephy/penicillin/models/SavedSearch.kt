package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLambda
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.special.CreatedAt


class SavedSearch(override val json: JsonObject): PenicillinModel {
    val createdAt by json.byLambda("created_at") { CreatedAt(string) }
    val id by json.byLong
    val idStr by json.byString("id_str")
    val name by json.byString
    val position by json.byString
    val query by json.byString
}
