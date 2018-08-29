package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString


data class Guest(override val json: JsonObject): PenicillinModel {
    val guestToken by json.byString("guest_token")
}
