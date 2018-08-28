package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class Image(override val json: JsonObject): PenicillinModel {
    val imageType by json.byString("image_type")
    val w by json.byInt
    val h by json.byInt
}

