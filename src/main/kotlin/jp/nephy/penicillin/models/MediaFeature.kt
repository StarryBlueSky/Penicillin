package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel


data class MediaFeature(override val json: JsonObject): PenicillinModel {
    val large by json.byModel<Face>()
    val medium by json.byModel<Face>()
    val orig by json.byModel<Face>()
    val small by json.byModel<Face>()
}
