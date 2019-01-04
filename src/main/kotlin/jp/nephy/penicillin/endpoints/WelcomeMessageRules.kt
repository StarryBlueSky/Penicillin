@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.WelcomeMessageRule

class WelcomeMessageRules(override val client: PenicillinClient): Endpoint {
    fun delete(id: Long, vararg options: Pair<String, String>) = client.session.delete("/1.1/direct_messages/welcome_messages/rules/destroy.json") {
        parameter("id" to id, *options)
    }.empty()

    fun create(vararg options: Pair<String, String>) = client.session.post("/1.1/direct_messages/welcome_messages/rules/new.json") {
        body {
            json {
                add(*options)
            }
        }
    }.empty()

    fun show(id: Long, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/welcome_messages/rules/show.json") {
        parameter("id" to id, *options)
    }.jsonObject<WelcomeMessageRule>()

    fun list(count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/welcome_messages/rules/list.json") {
        parameter("count" to count, *options)
    }.jsonArray<WelcomeMessageRule>()
}
