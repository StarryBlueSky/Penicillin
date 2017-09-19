package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.converter.byModel

class ExtendedTweet(val json: JsonElement) {
    val displayTextRange by json.byList<Int>("display_text_range")
    val entities by json.byModel<StatusEntity>()
    val fullText by json.byNullableString("full_text")
}
