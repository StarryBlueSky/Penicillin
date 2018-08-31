package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.WelcomeMessage

class WelcomeMessage(override val client: PenicillinClient): Endpoint {
    fun create(vararg options: Pair<String, String>) = client.session.post("/1.1/direct_messages/welcome_messages/new.json") {
        body {
            json {
                add(*options)
            }
        }
    }.empty()

    fun delete(id: Long, vararg options: Pair<String, String>) = client.session.delete("/1.1/direct_messages/welcome_messages/destroy.json") {
        parameter("id" to id, *options)
    }.empty()

    fun show(id: Long, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/welcome_messages/show.json") {
        parameter("id" to id, *options)
    }.jsonObject<WelcomeMessage>()

    fun list(count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/welcome_messages/list.json") {
        parameter("count" to count, *options)
    }.jsonArray<WelcomeMessage>()
}
