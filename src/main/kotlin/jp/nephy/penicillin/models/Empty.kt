@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject

data class Empty(override val json: ImmutableJsonObject): PenicillinModel
