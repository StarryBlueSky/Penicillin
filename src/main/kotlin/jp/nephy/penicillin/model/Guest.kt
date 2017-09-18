package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class Guest(val json: JsonElement) {
    val guestToken by json.byString("guest_token")
}