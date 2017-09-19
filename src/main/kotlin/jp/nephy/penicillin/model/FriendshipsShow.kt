package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class FriendshipsShow(val json: JsonElement) {
    val relationship by json.byModel<Relationship>()
}
