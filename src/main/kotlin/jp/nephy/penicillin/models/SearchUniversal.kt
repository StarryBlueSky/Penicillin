@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.lambda
import jp.nephy.jsonkt.delegation.model

data class SearchUniversal(override val json: JsonObject): PenicillinModel {
    val metadata by model<SearchUniversalMetadata>(key = "metadata")
    val statuses by lambda("modules") {
        it.jsonArray.map { json -> json.jsonObject }.filter { json -> json.containsKey("status") }.map { json -> StatusModule(json["status"].jsonObject) }
    }
    val userGalleries by lambda("modules") {
        it.jsonArray.map { json -> json.jsonObject }.filter { json -> json.containsKey("user_gallery") }.map { json -> UserGalleryModule(json["user_gallery"].jsonObject) }
    }
}
