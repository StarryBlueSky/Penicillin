package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString


class Privacy(override val json: JsonObject): PenicillinModel {
    val privacy by json.byString
}
