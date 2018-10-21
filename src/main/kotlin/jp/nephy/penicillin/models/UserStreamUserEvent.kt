@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject

data class UserStreamUserEvent(val parentJson: ImmutableJsonObject): UserStreamEvent(parentJson)
