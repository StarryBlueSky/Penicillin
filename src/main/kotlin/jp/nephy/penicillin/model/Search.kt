package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.bySearchMetadata
import jp.nephy.penicillin.converter.byStatusArray

class Search(val json: JsonElement) {
    val searchMetadata by json.bySearchMetadata("search_metadata")
    val statuses by json.byStatusArray
}
