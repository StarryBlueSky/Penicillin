package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byPhotoSize

class Photo(val json: JsonElement) {
    val large by json.byPhotoSize
    val medium by json.byPhotoSize
    val small by json.byPhotoSize
    val thumb by json.byPhotoSize
}
