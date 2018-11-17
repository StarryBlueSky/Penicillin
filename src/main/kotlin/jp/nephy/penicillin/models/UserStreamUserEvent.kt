@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject

data class UserStreamUserEvent(val parentJson: JsonObject): UserStreamEvent(parentJson)
