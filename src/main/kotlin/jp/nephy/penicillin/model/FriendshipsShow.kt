package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel

@Suppress("UNUSED")
class FriendshipsShow(override val json: JsonObject): JsonModel {
    val relationship by json.byModel<Relationship>()
}
