package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class MomentGuide(override val json: JsonObject): PenicillinModel {
    private val category by immutableJsonObject
    val categoryId by category.byString("category_id")
    val categoryName by category.byString("name")
    val categoryUri by category.byString("uri")
    val impressionId by long("impression_id")
    val cursor by string("scroll_cursor")

    val modules by modelList<MomentModule>()
    val trendModule by model<MomentTrendModule?>()
}
