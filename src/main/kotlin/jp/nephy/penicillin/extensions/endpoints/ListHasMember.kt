@file:Suppress("UNUSED", "PublicApiImplicitType")

package jp.nephy.penicillin.extensions.endpoints

import jp.nephy.penicillin.core.exceptions.PenicillinTwitterApiException
import jp.nephy.penicillin.core.exceptions.TwitterApiError
import jp.nephy.penicillin.core.request.action.ApiAction
import jp.nephy.penicillin.endpoints.Lists
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.lists
import jp.nephy.penicillin.endpoints.lists.member
import jp.nephy.penicillin.extensions.DelegatedAction
import jp.nephy.penicillin.extensions.complete
import jp.nephy.penicillin.extensions.execute
import jp.nephy.penicillin.models.TwitterList
import jp.nephy.penicillin.models.User

/**
 * Checks if the specified user is a member of the specified list.
 */
operator fun TwitterList.contains(user: User): Boolean {
    return client.lists.hasMember(id, user.id).complete()
}

/**
 * Returns true if the specified user is a member of the specified list.
 *
 * @param listId The numerical id of the list.
 * @param userId The ID of the user for whom to return results. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [ApiAction] for [Boolean].
 */
fun Lists.hasMember(
    listId: Long,
    userId: Long,
    vararg options: Option
) = hasMember(listId, null, null, null, userId, null, *options)

/**
 * Returns true if the specified user is a member of the specified list.
 *
 * @param listId The numerical id of the list.
 * @param screenName The screen name of the user for whom to return results. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [ApiAction] for [Boolean].
 */
fun Lists.hasMember(
    listId: Long,
    screenName: String,
    vararg options: Option
) = hasMember(listId, null, null, null, null, screenName, *options)

/**
 * Returns true if the specified user is a member of the specified list.
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerScreenName The screen name of the user who owns the list being requested by a slug.
 * @param userId The ID of the user for whom to return results. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [ApiAction] for [Boolean].
 */
fun Lists.hasMember(
    slug: String,
    ownerScreenName: String,
    userId: Long,
    vararg options: Option
) = hasMember(null, slug, ownerScreenName, null, userId, null, *options)

/**
 * Returns true if the specified user is a member of the specified list.
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerScreenName The screen name of the user who owns the list being requested by a slug.
 * @param screenName The screen name of the user for whom to return results. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [ApiAction] for [Boolean].
 */
fun Lists.hasMember(
    slug: String,
    ownerScreenName: String,
    screenName: String,
    vararg options: Option
) = hasMember(null, slug, ownerScreenName, null, null, screenName, *options)

/**
 * Returns true if the specified user is a member of the specified list.
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerId The user ID of the user who owns the list being requested by a slug.
 * @param userId The ID of the user for whom to return results. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [ApiAction] for [Boolean].
 */
fun Lists.hasMember(
    slug: String,
    ownerId: Long,
    userId: Long,
    vararg options: Option
) = hasMember(null, slug, null, ownerId, userId, null, *options)

/**
 * Returns true if the specified user is a member of the specified list.
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerId The user ID of the user who owns the list being requested by a slug.
 * @param screenName The screen name of the user for whom to return results. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [ApiAction] for [Boolean].
 */
fun Lists.hasMember(
    slug: String,
    ownerId: Long,
    screenName: String,
    vararg options: Option
) = hasMember(null, slug, null, ownerId, null, screenName, *options)


private fun Lists.hasMember(
    listId: Long? = null,
    slug: String? = null,
    ownerScreenName: String? = null,
    ownerId: Long? = null,
    userId: Long? = null,
    screenName: String? = null,
    vararg options: Option
): ApiAction<Boolean> = DelegatedAction {
    runCatching {
        member(listId, slug, ownerScreenName, ownerId, userId, screenName, includeEntities = false, skipStatus = true, options = *options).execute()
        true
    }.recover {
        if (it is PenicillinTwitterApiException && it.error == TwitterApiError.UserNotInThisList) {
            false
        } else {
            throw it
        }
    }.getOrThrow()
}
