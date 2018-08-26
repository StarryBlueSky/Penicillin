package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel


class FriendshipsShow(override val json: JsonObject): PenicillinModel {
    val relationship by json.byModel<Relationship>()
}
