package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement

class FriendshipsNoRetweetsIds(val json: JsonElement) {
    val ids = json.asJsonArray // []
}
