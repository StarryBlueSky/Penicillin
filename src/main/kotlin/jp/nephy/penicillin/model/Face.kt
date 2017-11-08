package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

@Suppress("UNUSED")
class Face(val json: JsonElement) {
    val faces by json.byList<FaceCoordinate>()
}
