package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byIntList
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byNullableString


data class ExtendedEntity(override val json: JsonObject): PenicillinModel {
    val displayTextRange by json.byIntList("display_text_range")
    val media by json.byModelList<MediaEntity>()
    val fullText by json.byNullableString("full_text")
}
