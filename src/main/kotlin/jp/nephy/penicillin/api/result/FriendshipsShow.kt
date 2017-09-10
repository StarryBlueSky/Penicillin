package jp.nephy.penicillin.api.result

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byRelationship

class FriendshipsShow(val json: JsonElement) {
    val relationship by json.byRelationship
}
