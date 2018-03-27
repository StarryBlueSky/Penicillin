package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.Cursorable
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.misc.StatusID
import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.model.List
import jp.nephy.penicillin.model.Status
import jp.nephy.penicillin.model.User
import jp.nephy.penicillin.parameters.ListCreationMode
import jp.nephy.penicillin.response.ResponseCursorObject
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject


class List(private val client: Client) {
    @GET
    fun getList(listId: Long?=null, slug: String?=null, ownerScreenName: String?=null, ownerId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<List> {
        return client.session.new()
                .url("/lists/show.json")
                .param("list_id" to listId)
                .param("slug" to slug)
                .param("owner_screen_name" to ownerScreenName)
                .param("owner_id" to ownerId)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getLists(userId: Long?=null, screenName: String?=null, reverse: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<List> {
        return client.session.new()
                .url("/lists/list.json")
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("reverse" to reverse)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getTimeline(listId: Long?=null, slug: String?=null, ownerScreenName: String?=null, ownerId: Long?=null, sinceId: StatusID?=null, maxId: StatusID?=null, count: Int?=null, includeEntities: Boolean?=null, includeRTs: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<Status> {
        return client.session.new()
                .url("/lists/statuses.json")
                .param("list_id" to listId)
                .param("slug" to slug)
                .param("owner_screen_name" to ownerScreenName)
                .param("owner_id" to ownerId)
                .param("since_id" to sinceId)
                .param("max_id" to maxId)
                .param("count" to count)
                .param("include_entities" to includeEntities)
                .param("include_rts" to includeRTs)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET @Cursorable
    fun getMembers(listId: Long?=null, slug: String?=null, ownerScreenName: String?=null, ownerId: Long?=null, count: Int?=null, includeEntities: Boolean?=null, skipStatus: Boolean?=null, vararg options: Pair<String, String?>): ResponseCursorObject<CursorUsers> {
        return client.session.new()
                .url("/lists/members.json")
                .param("list_id" to listId)
                .param("slug" to slug)
                .param("owner_screen_name" to ownerScreenName)
                .param("owner_id" to ownerId)
                .param("count" to count)
                .param("include_entities" to includeEntities)
                .param("skip_status" to skipStatus)
                .params(*options)
                .get()
                .getResponseCursorObject()
    }

    @GET @Cursorable
    fun getSubscribers(listId: Long?=null, slug: String?=null, ownerScreenName: String?=null, ownerId: Long?=null, count: Int?=null, includeEntities: Boolean?=null, skipStatus: Boolean?=null, vararg options: Pair<String, String?>): ResponseCursorObject<CursorUsers> {
        return client.session.new()
                .url("/lists/subscribers.json")
                .param("list_id" to listId)
                .param("slug" to slug)
                .param("owner_screen_name" to ownerScreenName)
                .param("owner_id" to ownerId)
                .param("count" to count)
                .param("include_entities" to includeEntities)
                .param("skip_status" to skipStatus)
                .params(*options)
                .get()
                .getResponseCursorObject()
    }

    @GET @Cursorable
    fun getOwnerships(userId: Long?=null, screenName: String?=null, count: Int?=null, vararg options: Pair<String, String?>): ResponseCursorObject<CursorLists> {
        return client.session.new()
                .url("/lists/ownerships.json")
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("count" to count)
                .params(*options)
                .get()
                .getResponseCursorObject()
    }

    @GET @Cursorable
    fun getMemberships(userId: Long?=null, screenName: String?=null, count: Int?=null, filterToOwnedLists: Boolean?=null, vararg options: Pair<String, String?>): ResponseCursorObject<CursorLists> {
        return client.session.new()
                .url("/lists/memberships.json")
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("count" to count)
                .param("filter_to_owned_lists" to filterToOwnedLists)
                .params(*options)
                .get()
                .getResponseCursorObject()
    }

    @GET @Cursorable
    fun getSubscriptions(userId: Long?=null, screenName: String?=null, count: Int?=null, vararg options: Pair<String, String?>): ResponseCursorObject<CursorLists> {
        return client.session.new()
                .url("/lists/subscriptions.json")
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("count" to count)
                .params(*options)
                .get()
                .getResponseCursorObject()
    }

    @GET
    fun getMember(listId: Long?=null, slug: String?=null, userId: Long?=null, screenName: String?=null, ownerScreenName: String?=null, ownerId: Long?=null, includeEntities: Boolean?=null, skipStatus: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/lists/members/show.json")
                .param("list_id" to listId)
                .param("slug" to slug)
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("owner_screen_name" to ownerScreenName)
                .param("owner_id" to ownerId)
                .param("include_entities" to includeEntities)
                .param("skip_status" to skipStatus)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getSubscriber(screenName: String?=null, ownerScreenName: String?=null, listId: Long?=null, slug: String?=null, userId: Long?=null, ownerId: Long?=null, includeEntities: Boolean?=null, skipStatus: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/lists/subscribers/show.json")
                .param("owner_screen_name" to ownerScreenName)
                .param("owner_id" to ownerId)
                .param("list_id" to listId)
                .param("slug" to slug)
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("include_entities" to includeEntities)
                .param("skip_status" to skipStatus)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST
    fun create(name: String, mode: ListCreationMode?=null, description: String?=null, vararg options: Pair<String, String?>): ResponseObject<List> {
        return client.session.new()
                .url("/lists/create.json")
                .dataAsForm("name" to name)
                .dataAsForm("mode" to when (mode) {
                    ListCreationMode.Public -> "public"
                    ListCreationMode.Private -> "private"
                    else -> null
                })
                .dataAsForm("description" to description)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun destroy(ownerScreenName: String?=null, ownerId: Long?=null, listId: Long?=null, slug: String?=null, vararg options: Pair<String, String?>): ResponseObject<List> {
        return client.session.new()
                .url("/lists/destroy.json")
                .dataAsForm("owner_screen_name" to ownerScreenName)
                .dataAsForm("owner_id" to ownerId)
                .dataAsForm("list_id" to listId)
                .dataAsForm("slug" to slug)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun update(listId: Long?=null, slug: String?=null, name: String?=null, mode: ListCreationMode?=null, description: String?=null, ownerScreenName: String?=null, ownerId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/lists/update.json")
                .dataAsForm("list_id" to listId)
                .dataAsForm("slug" to slug)
                .dataAsForm("name" to name)
                .dataAsForm("mode" to when (mode) {
                    ListCreationMode.Public -> "public"
                    ListCreationMode.Private -> "private"
                    else -> null
                })
                .dataAsForm("description" to description)
                .dataAsForm("owner_screen_name" to ownerScreenName)
                .dataAsForm("owner_id" to ownerId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun addMember(listId: Long?=null, slug: String?=null, userId: Long?=null, screenName: String?=null, ownerScreenName: String?=null, ownerId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/lists/members/create.json")
                .dataAsForm("list_id" to listId)
                .dataAsForm("slug" to slug)
                .dataAsForm("user_id" to userId)
                .dataAsForm("screen_name" to screenName)
                .dataAsForm("owner_screen_name" to ownerScreenName)
                .dataAsForm("owner_id" to ownerId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun addMembers(listId: Long?=null, slug: String?=null, userIds: Array<Long>?=null, screenNames: Array<String>?=null, ownerScreenName: String?=null, ownerId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/lists/members/create_all.json")
                .dataAsForm("list_id" to listId)
                .dataAsForm("slug" to slug)
                .dataAsForm("user_id" to userIds?.joinToString(","))
                .dataAsForm("screen_name" to screenNames?.joinToString(","))
                .dataAsForm("owner_screen_name" to ownerScreenName)
                .dataAsForm("owner_id" to ownerId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun removeMember(listId: Long?=null, slug: String?=null, userId: Long?=null, screenName: String?=null, ownerScreenName: String?=null, ownerId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/lists/members/destroy.json")
                .dataAsForm("list_id" to listId)
                .dataAsForm("slug" to slug)
                .dataAsForm("user_id" to userId)
                .dataAsForm("screen_name" to screenName)
                .dataAsForm("owner_screen_name" to ownerScreenName)
                .dataAsForm("owner_id" to ownerId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun removeMembers(listId: Long?=null, slug: String?=null, userIds: Array<Long>?=null, screenNames: Array<String>?=null, ownerScreenName: String?=null, ownerId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/lists/members/destroy_all.json")
                .dataAsForm("list_id" to listId)
                .dataAsForm("slug" to slug)
                .dataAsForm("user_id" to userIds?.joinToString(","))
                .dataAsForm("screen_name" to screenNames?.joinToString(","))
                .dataAsForm("owner_screen_name" to ownerScreenName)
                .dataAsForm("owner_id" to ownerId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun subscribe(ownerScreenName: String?=null, ownerId: Long?=null, listId: Long?=null, slug: String?=null, vararg options: Pair<String, String?>): ResponseObject<List> {
        return client.session.new()
                .url("/lists/subscribers/create.json")
                .dataAsForm("owner_screen_name" to ownerScreenName)
                .dataAsForm("owner_id" to ownerId)
                .dataAsForm("list_id" to listId)
                .dataAsForm("slug" to slug)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun unsubscribe(listId: Long?=null, slug: String?=null, ownerScreenName: String?=null, ownerId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/lists/subscribers/destroy.json")
                .dataAsForm("list_id" to listId)
                .dataAsForm("slug" to slug)
                .dataAsForm("owner_screen_name" to ownerScreenName)
                .dataAsForm("owner_id" to ownerId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }
}
