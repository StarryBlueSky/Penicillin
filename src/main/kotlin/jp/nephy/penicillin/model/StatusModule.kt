package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel

class StatusModule(override val json: JsonObject): JsonModel {
    val metadata by json.byModel<StatusMetadata>()
    val data by json.byModel<Status>()
}
