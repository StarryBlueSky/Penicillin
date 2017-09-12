package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.Cursorable
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.misc.StatusID
import jp.nephy.penicillin.model.Empty
import jp.nephy.penicillin.parameters.CollectionCreationTimelineOrder
import jp.nephy.penicillin.response.ResponseObject
import jp.nephy.penicillin.result.CollectionsDestroy
import jp.nephy.penicillin.result.CollectionsEntries
import jp.nephy.penicillin.result.CollectionsList
import jp.nephy.penicillin.result.CollectionsShow
import java.net.URL

class Collection(private val client: Client) {
    @GET
    fun getCollection(id: String, vararg options: Pair<String, String?>): ResponseObject<CollectionsShow> {
        return client.session.new()
                .url("/collections/show.json")
                .param("id" to id)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getCollections(id: String, count: Int?=null, maxPosition: Int?=null, minPosition: Int?=null, vararg options: Pair<String, String?>): ResponseObject<CollectionsEntries> {
        return client.session.new()
                .url("/collections/entries.json")
                .param("id" to id)
                .param("count" to count)
                .param("max_position" to maxPosition)
                .param("min_position" to minPosition)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @Cursorable
    fun getList(userId: Long?=null, screenName: String?=null, tweetId: StatusID?=null, count: Int?=null, vararg options: Pair<String, String?>): ResponseObject<CollectionsList> {
        return client.session.new()
                .url("/collections/list.json")
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("tweet_id" to tweetId)
                .param("count" to count)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST
    fun create(name: String, description: String?=null, url: URL?=null, timelineOrder: CollectionCreationTimelineOrder?=null, vararg options: Pair<String, String?>): ResponseObject<CollectionsShow> {
        return client.session.new()
                .url("/collections/create.json")
                .dataAsForm("name" to name)
                .dataAsForm("description" to description)
                .dataAsForm("url" to url)
                .dataAsForm("timeline_order" to when (timelineOrder) {
                    CollectionCreationTimelineOrder.OrderAdded -> "tweet_chron"
                    CollectionCreationTimelineOrder.MostRecentFirst -> "tweet_reverse_chron"
                    CollectionCreationTimelineOrder.OldestFirst -> "curation_reverse_chron"
                    else -> null
                })
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun destroy(id: String, vararg options: Pair<String, String?>): ResponseObject<CollectionsDestroy> {
        return client.session.new()
                .url("/collections/destroy.json")
                .dataAsForm("id" to id)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun update(id: String, name: String?=null, description: String?=null, url: URL?=null, vararg options: Pair<String, String?>): ResponseObject<CollectionsShow> {
        return client.session.new()
                .url("/collections/update.json")
                .dataAsForm("id" to id)
                .dataAsForm("name" to name)
                .dataAsForm("description" to description)
                .dataAsForm("url" to url)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun addEntry(id: String, tweetId: StatusID, relativeTo: StatusID?=null, above: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/collections/entries/add.json")
                .dataAsForm("id" to id)
                .dataAsForm("tweet_id" to tweetId)
                .dataAsForm("relative_to" to relativeTo)
                .dataAsForm("above" to above)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun curateEntries(vararg options: Pair<String, String>): ResponseObject<Empty> {
        return client.session.new()
                .url("/collections/entries/curate.json")
                .dataAsJson(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun moveEntry(id: String, tweetId: StatusID, relativeTo: StatusID, above: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/collections/entries/move.json")
                .dataAsForm("id" to id)
                .dataAsForm("tweet_id" to tweetId)
                .dataAsForm("relative_to" to relativeTo)
                .dataAsForm("above" to above)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun removeEntry(id: String, tweetId: StatusID, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/collections/entries/remove.json")
                .dataAsForm("id" to id)
                .dataAsForm("tweet_id" to tweetId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }
}
