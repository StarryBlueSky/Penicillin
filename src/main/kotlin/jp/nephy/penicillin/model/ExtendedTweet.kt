package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byNullableIntArray
import jp.nephy.penicillin.converter.byStatusEntity

class ExtendedTweet(val json: JsonElement) {
    val displayTextRange by json.byNullableIntArray("display_text_range")
    val entities by json.byStatusEntity
    val fullText by json.byNullableString("full_text")
}
