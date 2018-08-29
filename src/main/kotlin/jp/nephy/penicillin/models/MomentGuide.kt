package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString


data class MomentGuide(override val json: JsonObject): PenicillinModel {
    val categoryId by json["category"].byString("category_id")
    val categoryName by json["category"].byString("name")
    val categoryUri by json["category"].byString("uri")
    val impressionId by json.byLong("impression_id")
    val cursor by json.byString("scroll_cursor")

    val modules by json.byModelList<MomentModule>()
    val trendModule by json.byModel<MomentTrendModule?>()
}
