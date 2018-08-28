package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList


class Search(override val json: JsonObject): PenicillinModel {
    val searchMetadata by json.byModel<SearchMetadata>(key = "search_metadata")
    val statuses by json.byModelList<Status>()
}
