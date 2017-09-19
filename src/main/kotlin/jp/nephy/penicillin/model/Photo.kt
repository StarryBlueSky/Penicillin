package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class Photo(val json: JsonElement) {
    val large by json.byModel<PhotoSize>()
    val medium by json.byModel<PhotoSize>()
    val small by json.byModel<PhotoSize>()
    val thumb by json.byModel<PhotoSize>()
}
