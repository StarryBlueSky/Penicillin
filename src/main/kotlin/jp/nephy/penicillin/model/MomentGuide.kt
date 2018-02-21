package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*

@Suppress("UNUSED")
class MomentGuide(override val json: JsonObject): JsonModel {
    val categoryId by json["category"].byString("category_id")
    val categoryName by json["category"].byString("name")
    val categoryUri by json["category"].byString("uri")
    val impressionId by json.byLong("impression_id")
    val cursor by json.byString("scroll_cursor")

    val modules by json.byModelList<MomentModule>()
    val trendModule by json.byModel<MomentTrendModule?>()
}
