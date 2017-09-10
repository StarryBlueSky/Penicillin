package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byFaceCoordinateArray

class Face(val json: JsonElement) {
    val faces by json.byFaceCoordinateArray
}
