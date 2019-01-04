@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class MomentGuide(override val json: JsonObject): PenicillinModel {
    private val category by jsonObject
    val categoryId by category.byString("category_id")
    val categoryName by category.byString("name")
    val categoryUri by category.byString("uri")
    val impressionId by long("impression_id")
    val cursor by string("scroll_cursor")
    val modules by modelList<Module>()
    val trendModule by model<TrendModule?>()

    data class Module(override val json: JsonObject): PenicillinModel {
        val moduleType by string("module_type")
        val moments by modelList<Moment>()
    }

    data class TrendModule(override val json: JsonObject): PenicillinModel {
        val metadata by model<TrendMetadata>()
        val trends by modelList<TrendType>()
    }
}
