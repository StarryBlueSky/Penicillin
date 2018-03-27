package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byIntList
import jp.nephy.jsonkt.byString


class SymbolEntity(override val json: JsonObject): JsonModel {
    val text by json.byString
    val indices by json.byIntList
}

