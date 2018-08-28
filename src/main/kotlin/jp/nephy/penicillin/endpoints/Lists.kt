package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoints.parameters.ListCreationMode
import jp.nephy.penicillin.models.*
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.User


class Lists(override val client: PenicillinClient): Endpoint {
    fun show(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/show.json") {
        parameter("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
    }.jsonObject<TwitterList>()

    fun list(userId: Long? = null, screenName: String? = null, reverse: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/list.json") {
        parameter("user_id" to userId, "screen_name" to screenName, "reverse" to reverse, *options)
    }.jsonArray<TwitterList>()

    fun timeline(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, includeRTs: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/statuses.json") {
        parameter("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "since_id" to sinceId, "max_id" to maxId, "count" to count, "include_entities" to includeEntities, "include_rts" to includeRTs, *options)
    }.jsonArray<Status>()

    fun members(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/members.json") {
        parameter("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "count" to count, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }.cursorJsonObject<CursorUsers>()

    fun subscribers(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/subscribers.json") {
        parameter("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "count" to count, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }.cursorJsonObject<CursorUsers>()

    fun ownerships(userId: Long? = null, screenName: String? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/ownerships.json") {
        parameter("user_id" to userId, "screen_name" to screenName, "count" to count, *options)
    }.cursorJsonObject<CursorLists>()

    fun memberships(userId: Long? = null, screenName: String? = null, count: Int? = null, filterToOwnedLists: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/memberships.json") {
        parameter("user_id" to userId, "screen_name" to screenName, "count" to count, "filter_to_owned_lists" to filterToOwnedLists, *options)
    }.cursorJsonObject<CursorLists>()

    fun subscriptions(userId: Long? = null, screenName: String? = null, count: Int? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/subscriptions.json") {
        parameter("user_id" to userId, "screen_name" to screenName, "count" to count, *options)
    }.cursorJsonObject<CursorLists>()

    fun member(listId: Long? = null, slug: String? = null, userId: Long? = null, screenName: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/members/show.json") {
        parameter("list_id" to listId, "slug" to slug, "user_id" to userId, "screen_name" to screenName, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }.jsonObject<User>()

    fun subscriber(screenName: String? = null, ownerScreenName: String? = null, listId: Long? = null, slug: String? = null, userId: Long? = null, ownerId: Long? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/lists/subscribers/show.json") {
        parameter("owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "list_id" to listId, "slug" to slug, "user_id" to userId, "screen_name" to screenName, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }.jsonObject<User>()

    fun create(name: String, mode: ListCreationMode? = null, description: String? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/lists/create.json") {
        body {
            form {
                add("name" to name, "mode" to mode?.value, "description" to description, *options)
            }
        }
    }.jsonObject<TwitterList>()

    fun destroy(ownerScreenName: String? = null, ownerId: Long? = null, listId: Long? = null, slug: String? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/lists/destroy.json") {
        body {
            form {
                add("owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "list_id" to listId, "slug" to slug, *options)
            }
        }
    }.jsonObject<TwitterList>()

    fun update(listId: Long? = null, slug: String? = null, name: String? = null, mode: ListCreationMode? = null, description: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/lists/update.json") {
        body {
            form {
                add("list_id" to listId, "slug" to slug, "name" to name, "mode" to mode?.value, "description" to description, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
            }
        }
    }.emptyJsonObject()

    fun addMember(listId: Long? = null, slug: String? = null, userId: Long? = null, screenName: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/lists/members/create.json") {
        body {
            form {
                add("list_id" to listId, "slug" to slug, "user_id" to userId, "screen_name" to screenName, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
            }
        }
    }.emptyJsonObject()

    fun addMembers(listId: Long? = null, slug: String? = null, userIds: List<Long>? = null, screenNames: List<String>? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/lists/members/create_all.json") {
        body {
            form {
                add("list_id" to listId, "slug" to slug, "user_id" to userIds?.joinToString(","), "screen_name" to screenNames?.joinToString(","), "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
            }
        }
    }.emptyJsonObject()

    fun removeMember(listId: Long? = null, slug: String? = null, userId: Long? = null, screenName: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/lists/members/destroy.json") {
        body {
            form {
                add("list_id" to listId, "slug" to slug, "user_id" to userId, "screen_name" to screenName, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
            }
        }
    }.emptyJsonObject()

    fun removeMembers(listId: Long? = null, slug: String? = null, userIds: List<Long>? = null, screenNames: List<String>? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/lists/members/destroy_all.json") {
        body {
            form {
                add("list_id" to listId, "slug" to slug, "user_id" to userIds?.joinToString(","), "screen_name" to screenNames?.joinToString(","), "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
            }
        }
    }.emptyJsonObject()

    fun subscribe(ownerScreenName: String? = null, ownerId: Long? = null, listId: Long? = null, slug: String? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/lists/subscribers/create.json") {
        body {
            form {
                add("owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "list_id" to listId, "slug" to slug, *options)
            }
        }
    }.jsonObject<TwitterList>()

    fun unsubscribe(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/lists/subscribers/destroy.json") {
        body {
            form {
                add("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
            }
        }
    }.emptyJsonObject()
}
