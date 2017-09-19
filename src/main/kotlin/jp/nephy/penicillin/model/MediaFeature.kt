package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class MediaFeature(val json: JsonElement) {
    val large by json.byModel<Face>()
    val medium by json.byModel<Face>()
    val orig by json.byModel<Face>()
    val small by json.byModel<Face>()
}
