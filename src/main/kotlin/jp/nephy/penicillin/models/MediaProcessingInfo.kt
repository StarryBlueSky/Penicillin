package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byNullableInt
import jp.nephy.jsonkt.byString


class MediaProcessingInfo(override val json: JsonObject): PenicillinModel {
    val checkAfterSecs by json.byNullableInt("check_after_secs")
    val error by json.byModel<Error?>()
    val progressPercent by json.byNullableInt("progress_percent")
    val state by json.byString
}
