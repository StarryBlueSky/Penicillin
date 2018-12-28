@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.Status

class Favorite(override val client: PenicillinClient): Endpoint {
    fun list(sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
            client.session.get("/1.1/favorites/list.json") {
                parameter("count" to count, "since_id" to sinceId, "max_id" to maxId, "include_entities" to includeEntities, *options)
            }.jsonArray<Status>()

    fun list(userId: Long, sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/favorites/list.json") {
            parameter("user_id" to userId, "count" to count, "since_id" to sinceId, "max_id" to maxId, "include_entities" to includeEntities, *options)
        }.jsonArray<Status>()

    fun list(screenName: String, sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) =
            client.session.get("/1.1/favorites/list.json") {
                parameter("screen_name" to screenName, "count" to count, "since_id" to sinceId, "max_id" to maxId, "include_entities" to includeEntities, *options)
            }.jsonArray<Status>()

    fun create(id: Long, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/favorites/create.json") {
        body {
            form {
                add("id" to id, "include_entities" to includeEntities, *options)
            }
        }
    }.jsonObject<Status>()

    fun destroy(id: Long, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/favorites/destroy.json") {
        body {
            form {
                add("id" to id, "include_entities" to includeEntities, *options)
            }
        }
    }.jsonObject<Status>()
}
