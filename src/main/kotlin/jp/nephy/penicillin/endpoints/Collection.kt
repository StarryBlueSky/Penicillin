@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoints.parameters.CollectionCreationTimelineOrder


class Collection(override val client: PenicillinClient): Endpoint {
    fun show(id: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/collections/show.json") {
        parameter("id" to id, *options)
    }.empty()

    fun list(userId: Long? = null, screenName: String? = null, tweetId: Long? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/collections/list.json") {
        parameter("user_id" to userId, "screen_name" to screenName, "tweet_id" to tweetId, "count" to count, *options)
    }.empty()

    fun create(name: String, description: String? = null, url: String? = null, timelineOrder: CollectionCreationTimelineOrder? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/collections/create.json") {
        body {
            form {
                add("name" to name, "description" to description, "url" to url, "timeline_order" to timelineOrder?.value, *options)
            }
        }
    }.empty()

    fun destroy(id: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/collections/destroy.json") {
        body {
            form {
                add("id" to id, *options)
            }
        }
    }.empty()

    fun update(id: String, name: String? = null, description: String? = null, url: String? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/collections/update.json") {
        body {
            form {
                add("id" to id, "name" to name, "description" to description, "url" to url, *options)
            }
        }
    }.empty()
}
