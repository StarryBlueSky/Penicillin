package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.converter.byModel

@Suppress("UNUSED")
class MomentGuide(val json: JsonElement) {
    val categoryId by json["category"].byString("category_id")
    val categoryName by json["category"].byString("name")
    val categoryUri by json["category"].byString("uri")
    val impressionId by json.byLong("impression_id")
    val cursor by json.byString("scroll_cursor")

    val modules by json.byList<MomentModule>()
    val trendModule by json.byModel<MomentTrendModule?>()
}
