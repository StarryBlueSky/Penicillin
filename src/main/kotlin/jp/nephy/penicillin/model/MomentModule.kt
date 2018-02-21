package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class MomentModule(override val json: JsonObject): JsonModel {
    val moduleType by json.byString("module_type")
    val moments by json.byModelList<Moment>()
}
