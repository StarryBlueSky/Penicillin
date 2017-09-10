package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byPhotoSize

class Photo(val json: JsonElement) {
    val large by json.byPhotoSize
    val medium by json.byPhotoSize
    val small by json.byPhotoSize
    val thumb by json.byPhotoSize
}
