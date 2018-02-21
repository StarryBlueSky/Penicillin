package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byIntList
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byNullableString

@Suppress("UNUSED")
class ExtendedTweet(override val json: JsonObject): JsonModel {
    val displayTextRange by json.byIntList("display_text_range")
    val entities by json.byModel<StatusEntity>()
    val fullText by json.byNullableString("full_text")
}
