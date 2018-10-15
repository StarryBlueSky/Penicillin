package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject

data class UserStreamUserEvent(override val json: JsonObject): UserStreamEvent()
