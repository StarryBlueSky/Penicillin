package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList


class CursorLists(json: JsonObject): Cursor(json) {
    val lists by json.byModelList<TwitterList>()
}
