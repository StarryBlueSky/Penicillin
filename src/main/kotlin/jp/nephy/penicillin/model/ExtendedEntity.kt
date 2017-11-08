package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

@Suppress("UNUSED")
class ExtendedEntity(val json: JsonElement) {
    val displayTextRange by json.byList<Int>("display_text_range")
    val media by json.byList<MediaEntity>()
    val fullText by json.byNullableString("full_text")
}
