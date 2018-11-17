@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject

data class Empty(override val json: JsonObject): PenicillinModel
