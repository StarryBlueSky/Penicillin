@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class MediaProcessingInfo(override val json: ImmutableJsonObject): PenicillinModel {
    val checkAfterSecs by nullableInt("check_after_secs")
    val error by model<Error?>()
    val progressPercent by nullableInt("progress_percent")
    val state by string
}
