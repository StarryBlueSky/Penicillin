package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.converter.byModel

@Suppress("UNUSED")
class Search(val json: JsonElement) {
    val searchMetadata by json.byModel<SettingMetadata>("search_metadata")
    val statuses by json.byList<Status>()
}
