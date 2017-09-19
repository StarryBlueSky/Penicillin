package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

class MomentModule(val json: JsonElement) {
    val moduleType by json.byString("module_type")
    val moments by json.byList<Moment>()
}
