@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient


class CollectionEntry(override val client: PenicillinClient): Endpoint {
    fun entries(id: String, count: Int? = null, maxPosition: Int? = null, minPosition: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/collections/entries.json") {
        parameter("id" to id, "count" to count, "max_position" to maxPosition, "min_position" to minPosition, *options)
    }.empty()

    fun add(id: String, tweetId: Long, relativeTo: Long? = null, above: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/collections/entries/add.json") {
        body {
            form {
                add("id" to id, "tweet_id" to tweetId, "relative_to" to relativeTo, "above" to above, *options)
            }
        }
    }.empty()

    fun curate(vararg options: Pair<String, String>) = client.session.post("/1.1/collections/entries/curate.json") {
        body {
            json {
                add(*options)
            }
        }
    }.empty()

    fun move(id: String, tweetId: Long, relativeTo: Long, above: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/collections/entries/move.json") {
        body {
            form {
                add("id" to id, "tweet_id" to tweetId, "relative_to" to relativeTo, "above" to above, *options)
            }
        }
    }.empty()

    fun remove(id: String, tweetId: Long, vararg options: Pair<String, Any?>) = client.session.post("/1.1/collections/entries/remove.json") {
        body {
            form {
                add("id" to id, "tweet_id" to tweetId, *options)
            }
        }
    }.empty()
}
