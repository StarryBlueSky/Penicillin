package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString


class MomentModule(override val json: JsonObject): PenicillinModel {
    val moduleType by json.byString("module_type")
    val moments by json.byModelList<Moment>()
}
