@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject

data class WelcomeMessage(override val json: ImmutableJsonObject): PenicillinModel
