package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLambda

data class FriendshipsNoRetweetsIds(override val json: JsonObject): PenicillinModel {
    val ids by json.byLambda {
        json.asJsonArray.map {
            it.asLong
        }
    }
}
