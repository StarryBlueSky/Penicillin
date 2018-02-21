package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel

@Suppress("UNUSED")
class Photo(override val json: JsonObject): JsonModel {
    val large by json.byModel<PhotoSize>()
    val medium by json.byModel<PhotoSize>()
    val small by json.byModel<PhotoSize>()
    val thumb by json.byModel<PhotoSize>()
}
