package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class ExtendedProfile(val json: JsonElement) {
    val id by json.byLong
    val idStr by json.byString
    val birthdate by json.byModel<Birthdate?>()
}