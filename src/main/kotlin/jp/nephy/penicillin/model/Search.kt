package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList


class Search(override val json: JsonObject): JsonModel {
    val searchMetadata by json.byModel<SettingMetadata>(key = "search_metadata")
    val statuses by json.byModelList<Status>()
}
