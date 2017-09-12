package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byNullableError

class MediaProcessingInfo(val json: JsonElement) {
    val checkAfterSecs by json.byNullableInt("check_after_secs")
    val error by json.byNullableError
    val progressPercent by json.byNullableInt("progress_percent")
    val state by json.byString
}
