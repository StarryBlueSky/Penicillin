package jp.nephy.penicillin.api.result

import com.github.salomonbrys.kotson.byBool
import com.google.gson.JsonElement

class CollectionsDestroy(val json: JsonElement) {
    val destroyed by json.byBool
}
