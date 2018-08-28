package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*


class SearchUniversal(override val json: JsonObject): PenicillinModel {
    val metadata by json.byModel<SearchUniversalMetadata>(key = "metadata")
    val statuses by json.byLambda("modules") { jsonArray.map { it.jsonObject }.filter { it.containsKey("status") }.map { StatusModule(it["status"].jsonObject) } }
    val userGalleries by json.byLambda("modules") { jsonArray.map { it.jsonObject }.filter { it.containsKey("user_gallery") }.map { UserGalleryModule(it["user_gallery"].jsonObject) } }
}
