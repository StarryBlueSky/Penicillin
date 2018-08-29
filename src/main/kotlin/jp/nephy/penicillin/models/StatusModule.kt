package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel

data class StatusModule(override val json: JsonObject): PenicillinModel {
    val metadata by json.byModel<StatusMetadata>()
    val data by json.byModel<Status>()
}
