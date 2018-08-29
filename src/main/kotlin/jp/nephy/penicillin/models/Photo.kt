package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel


data class Photo(override val json: JsonObject): PenicillinModel {
    val large by json.byModel<PhotoSize>()
    val medium by json.byModel<PhotoSize>()
    val small by json.byModel<PhotoSize>()
    val thumb by json.byModel<PhotoSize>()
}
