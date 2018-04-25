package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoint.parameter.ListCreationMode
import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.model.Status
import jp.nephy.penicillin.model.User


class Lists(override val client: PenicillinClient): Endpoint {
    fun show(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>)= client.session.getObject<TwitterList>("/lists/show.json") {
        query("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
    }

    fun list(userId: Long? = null, screenName: String? = null, reverse: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getObject<TwitterList>("/lists/list.json") {
        query("user_id" to userId, "screen_name" to screenName, "reverse" to reverse, *options)
    }

    fun timeline(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, sinceId: Long? = null, maxId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, includeRTs: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<Status>("/lists/statuses.json") {
        query("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "since_id" to sinceId, "max_id" to maxId, "count" to count, "include_entities" to includeEntities, "include_rts" to includeRTs, *options)
    }

    fun members(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorUsers>("/lists/members.json") {
        query("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "count" to count, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }

    fun subscribers(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, count: Int? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorUsers>("/lists/subscribers.json") {
        query("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "count" to count, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }

    fun ownerships(userId: Long? = null, screenName: String? = null, count: Int? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorLists>("/lists/ownerships.json") {
        query("user_id" to userId, "screen_name" to screenName, "count" to count, *options)
    }

    fun memberships(userId: Long? = null, screenName: String? = null, count: Int? = null, filterToOwnedLists: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorLists>("/lists/memberships.json") {
        query("user_id" to userId, "screen_name" to screenName, "count" to count, "filter_to_owned_lists" to filterToOwnedLists, *options)
    }

    fun subscriptions(userId: Long? = null, screenName: String? = null, count: Int? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorLists>("/lists/subscriptions.json") {
        query("user_id" to userId, "screen_name" to screenName, "count" to count, *options)
    }

    fun member(listId: Long? = null, slug: String? = null, userId: Long? = null, screenName: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getObject<User>("/lists/members/show.json") {
        query("list_id" to listId, "slug" to slug, "user_id" to userId, "screen_name" to screenName, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }

    fun subscriber(screenName: String? = null, ownerScreenName: String? = null, listId: Long? = null, slug: String? = null, userId: Long? = null, ownerId: Long? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getObject<User>("/lists/subscribers/show.json") {
        query("owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "list_id" to listId, "slug" to slug, "user_id" to userId, "screen_name" to screenName, "include_entities" to includeEntities, "skip_status" to skipStatus, *options)
    }

    fun create(name: String, mode: ListCreationMode? = null, description: String? = null, vararg options: Pair<String, Any?>)= client.session.postObject<TwitterList>("/lists/create.json") {
        form("name" to name, "mode" to mode?.value, "description" to description, *options)
    }

    fun destroy(ownerScreenName: String? = null, ownerId: Long? = null, listId: Long? = null, slug: String? = null, vararg options: Pair<String, Any?>)= client.session.postObject<TwitterList>("/lists/destroy.json") {
        form("owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "list_id" to listId, "slug" to slug, *options)
    }

    fun update(listId: Long? = null, slug: String? = null, name: String? = null, mode: ListCreationMode? = null, description: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/lists/update.json") {
        form("list_id" to listId, "slug" to slug, "name" to name, "mode" to mode?.value, "description" to description, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
    }

    fun addMember(listId: Long? = null, slug: String? = null, userId: Long? = null, screenName: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/lists/members/create.json") {
        form("list_id" to listId, "slug" to slug, "user_id" to userId, "screen_name" to screenName, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
    }

    fun addMembers(listId: Long? = null, slug: String? = null, userIds: List<Long>? = null, screenNames: List<String>? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/lists/members/create_all.json") {
        form("list_id" to listId, "slug" to slug, "user_id" to userIds?.joinToString(","), "screen_name" to screenNames?.joinToString(","), "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
    }

    fun removeMember(listId: Long? = null, slug: String? = null, userId: Long? = null, screenName: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/lists/members/destroy.json") {
        form("list_id" to listId, "slug" to slug, "user_id" to userId, "screen_name" to screenName, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
    }

    fun removeMembers(listId: Long? = null, slug: String? = null, userIds: List<Long>? = null, screenNames: List<String>? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/lists/members/destroy_all.json") {
        form("list_id" to listId, "slug" to slug, "user_id" to userIds?.joinToString(","), "screen_name" to screenNames?.joinToString(","), "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
    }

    fun subscribe(ownerScreenName: String? = null, ownerId: Long? = null, listId: Long? = null, slug: String? = null, vararg options: Pair<String, Any?>)= client.session.postObject<TwitterList>("/lists/subscribers/create.json") {
        form("owner_screen_name" to ownerScreenName, "owner_id" to ownerId, "list_id" to listId, "slug" to slug, *options)
    }

    fun unsubscribe(listId: Long? = null, slug: String? = null, ownerScreenName: String? = null, ownerId: Long? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("/lists/subscribers/destroy.json") {
        form("list_id" to listId, "slug" to slug, "owner_screen_name" to ownerScreenName, "owner_id" to ownerId, *options)
    }
}
