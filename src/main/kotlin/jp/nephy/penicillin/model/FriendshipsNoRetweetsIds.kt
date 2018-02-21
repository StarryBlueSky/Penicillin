package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byLambda

class FriendshipsNoRetweetsIds(override val json: JsonObject): JsonModel {
    val ids by json.byLambda {
        json.asJsonArray.map {
            it.asLong
        }
    }
}
