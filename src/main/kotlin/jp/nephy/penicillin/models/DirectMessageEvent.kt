package jp.nephy.penicillin.models

import com.google.gson.JsonObject

data class DirectMessageEvent(override val json: JsonObject): PenicillinModel
