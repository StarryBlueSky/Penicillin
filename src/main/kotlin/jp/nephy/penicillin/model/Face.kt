package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byFaceCoordinateArray

class Face(val json: JsonElement) {
    val faces by json.byFaceCoordinateArray
}
