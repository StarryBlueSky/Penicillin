package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.DirectMessage


class DirectMessage(override val client: PenicillinClient): Endpoint {
    fun show(id: Long, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/show.json") {
        parameter("id" to id, *options)
    }.jsonObject<DirectMessage>()

    fun list(sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages.json") {
        parameter("since_id" to sinceId, "max_id" to maxId, "count" to count, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }.jsonArray<DirectMessage>()

    fun sentMessages(sinceId: Long? = null, maxId: Long? = null, count: Int? = null, page: Int? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/direct_messages/sent.json") {
        parameter("since_id" to sinceId, "max_id" to maxId, "count" to count, "include_entities" to includeEntities, "page" to page, *options)
    }.jsonArray<DirectMessage>()

    fun create(text: String, userId: Long? = null, screenName: String? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/direct_messages/new.json") {
        body {
            form {
                add("text" to text, "user_id" to userId, "screen_name" to screenName, *options)
            }
        }
    }.jsonObject<DirectMessage>()

    fun delete(id: Long, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/direct_messages/destroy.json") {
        body {
            form {
                add("id" to id, "include_entities" to includeEntities, *options)
            }
        }
    }.jsonObject<DirectMessage>()
}
