package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel


class MediaFeature(override val json: JsonObject): JsonModel {
    val large by json.byModel<Face>()
    val medium by json.byModel<Face>()
    val orig by json.byModel<Face>()
    val small by json.byModel<Face>()
}
