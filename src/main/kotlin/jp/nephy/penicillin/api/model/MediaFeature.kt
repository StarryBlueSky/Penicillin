package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byFace

class MediaFeature(val json: JsonElement) {
    val large by json.byFace
    val medium by json.byFace
    val orig by json.byFace
    val small by json.byFace
}
