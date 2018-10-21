@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.lambda
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.immutableJsonArray
import jp.nephy.jsonkt.immutableJsonObject

data class SearchUniversal(override val json: ImmutableJsonObject): PenicillinModel {
    val metadata by model<SearchUniversalMetadata>(key = "metadata")
    val statuses by lambda("modules") {
        it.immutableJsonArray.map { json -> json.immutableJsonObject }.filter { json -> json.containsKey("status") }.map { json -> StatusModule(json["status"].immutableJsonObject) }
    }
    val userGalleries by lambda("modules") {
        it.immutableJsonArray.map { json -> json.immutableJsonObject }.filter { json -> json.containsKey("user_gallery") }.map { json -> UserGalleryModule(json["user_gallery"].immutableJsonObject) }
    }
}
