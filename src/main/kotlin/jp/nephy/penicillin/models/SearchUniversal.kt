@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class SearchUniversal(override val json: JsonObject): PenicillinModel {
    val metadata by model<Metadata>(key = "metadata")
    val statuses by lambda("modules") {
        it.jsonArray.map { json -> json.jsonObject }.filter { json -> json.containsKey("status") }.map { json -> StatusModule(json["status"].jsonObject) }
    }
    val userGalleries by lambda("modules") {
        it.jsonArray.map { json -> json.jsonObject }.filter { json -> json.containsKey("user_gallery") }.map { json -> UserGallery(json["user_gallery"].jsonObject) }
    }

    data class Metadata(override val json: JsonObject): PenicillinModel {
        val cursor by string
        val refreshIntervalInSec by int("refresh_interval_in_sec")
    }

    data class UserGallery(override val json: JsonObject): PenicillinModel {
        val metadata by model<Metadata>()
        val data by modelList<Data>()

        data class Metadata(override val json: JsonObject): PenicillinModel {
            val resultType by string("result_type")
        }

        data class Data(override val json: JsonObject): PenicillinModel {
            val metadata by model<Metadata>()
            val data by model<User>()

            data class Metadata(override val json: JsonObject): PenicillinModel {
                val resultType by string("result_type")
            }
        }
    }
}
