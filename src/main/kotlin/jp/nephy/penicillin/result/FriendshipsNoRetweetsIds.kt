package jp.nephy.penicillin.result

import com.google.gson.JsonElement

class FriendshipsNoRetweetsIds(val json: JsonElement) {
    val ids = arrayListOf<Long>().apply {
        json.asJsonArray.forEach {
            this.add(it.asLong)
        }
    }
}
