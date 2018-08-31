package jp.nephy.penicillin.models

import com.google.gson.JsonObject

data class UserStreamUserEvent(override val json: JsonObject): UserStreamEvent(json)
