package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byNullableIntArray
import jp.nephy.penicillin.converter.byNullableMediaEntityArray

class ExtendedEntity(val json: JsonElement) {
    val displayTextRange by json.byNullableIntArray("display_text_range")
    val media by json.byNullableMediaEntityArray
    val fullText by json.byNullableString("full_text")
}
