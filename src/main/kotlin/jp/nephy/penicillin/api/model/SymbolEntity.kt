package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byIntArray

class SymbolEntity(val json: JsonElement) {
    val text by json.byString
    val indices by json.byIntArray
}

