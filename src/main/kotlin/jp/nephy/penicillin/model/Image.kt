package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class Image(override val json: JsonObject): JsonModel {
    val imageType by json.byString("image_type")
    val w by json.byInt
    val h by json.byInt
}

