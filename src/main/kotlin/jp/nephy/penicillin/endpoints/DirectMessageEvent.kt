package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.DirectMessageEvent

class DirectMessageEvent(override val client: PenicillinClient): Endpoint {
    fun create(vararg options: Pair<String, String>) = client.session.post("/1.1/direct_messages/events/new.json") {
        body {
            json {
                add(*options)
            }
        }
    }.empty()

    fun show(id: Long, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/events/show.json") {
        parameter("id" to id, *options)
    }.jsonObject<DirectMessageEvent>()

    fun list(count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/events/list.json") {
        parameter("count" to count, *options)
    }.jsonArray<DirectMessageEvent>()
}
