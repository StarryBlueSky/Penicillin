package jp.nephy.penicillin.endpoints

import jp.nephy.jsonkt.jsonObject
import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.DirectMessageEventList
import jp.nephy.penicillin.models.DirectMessageEventShow

class DirectMessageEvent(override val client: PenicillinClient): Endpoint {
    fun create(userId: String, text: String, vararg options: Pair<String, String>) = client.session.post("/1.1/direct_messages/events/new.json") {
        body {
            json {
                add("event" to jsonObject(
                        "type" to "message_create",
                        "message_create" to jsonObject(
                                "target" to jsonObject(
                                        "recipient_id" to userId
                                ),
                                "message_data" to jsonObject(
                                        "text" to text
                                )
                        )
                ))
                add(*options)
            }
        }
    }.jsonObject<DirectMessageEventShow>()

    fun delete(id: String, vararg options: Pair<String, Any?>) = client.session.delete("/1.1/direct_messages/events/destroy.json") {
        parameter("id" to id, *options)
    }.text()

    fun list(count: Int? = null, cursor: String? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/events/list.json") {
        parameter("count" to count, "cursor" to cursor, *options)
    }.jsonObject<DirectMessageEventList>()

    fun show(id: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/events/show.json") {
        parameter("id" to id, *options)
    }.jsonObject<DirectMessageEventShow>()
}
