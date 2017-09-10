package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.bySearchMetadata
import jp.nephy.penicillin.api.byStatusArray

class Search(val json: JsonElement) {
    val searchMetadata by json.bySearchMetadata("search_metadata")
    val statuses by json.byStatusArray
}
