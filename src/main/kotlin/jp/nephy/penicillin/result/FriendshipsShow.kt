package jp.nephy.penicillin.result

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byRelationship

class FriendshipsShow(val json: JsonElement) {
    val relationship by json.byRelationship
}
