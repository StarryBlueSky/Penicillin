package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byLambda

class FriendshipsNoRetweetsIds(val json: JsonElement) {
    val ids by json.byLambda {
        arrayListOf<Long>().apply {
            json.asJsonArray.forEach {
                this.add(it.asLong)
            }
        }
    }
}
