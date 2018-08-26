package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.Empty


class CollectionEntry(override val client: PenicillinClient): Endpoint {
    fun entries(id: String, count: Int? = null, maxPosition: Int? = null, minPosition: Int? = null, vararg options: Pair<String, Any?>)= client.session.getObject<Empty>("/collections/entries.json") {
        query("id" to id, "count" to count, "max_position" to maxPosition, "min_position" to minPosition, *options)
    }

    fun add(id: String, tweetId: Long, relativeTo: Long? = null, above: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/collections/entries/add.json") {
        form("id" to id, "tweet_id" to tweetId, "relative_to" to relativeTo, "above" to above, *options)
    }

    fun curate(vararg options: Pair<String, String>)= client.session.postObject<Empty>("/collections/entries/curate.json") {
        json(*options)
    }

    fun move(id: String, tweetId: Long, relativeTo: Long, above: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/collections/entries/move.json") {
        form("id" to id, "tweet_id" to tweetId, "relative_to" to relativeTo, "above" to above, *options)
    }

    fun remove(id: String, tweetId: Long, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/collections/entries/remove.json") {
        form("id" to id, "tweet_id" to tweetId, *options)
    }
}
