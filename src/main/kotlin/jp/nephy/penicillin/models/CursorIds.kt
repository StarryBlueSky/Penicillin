package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLongList


class CursorIds(json: JsonObject): PenicillinCursorModel(json) {
    val ids by json.byLongList
}
