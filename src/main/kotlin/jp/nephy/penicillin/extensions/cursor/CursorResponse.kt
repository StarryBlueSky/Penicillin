@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions.cursor

import jp.nephy.penicillin.core.response.CursorJsonObjectResponse
import jp.nephy.penicillin.models.CursorIds
import jp.nephy.penicillin.models.CursorLists
import jp.nephy.penicillin.models.CursorUsers

val Sequence<CursorJsonObjectResponse<CursorIds>>.allIds
    get() = toList().flatMap { it.result.ids }
val Sequence<CursorJsonObjectResponse<CursorLists>>.allLists
    get() = toList().flatMap { it.result.lists }
val Sequence<CursorJsonObjectResponse<CursorUsers>>.allUsers
    get() = toList().flatMap { it.result.users }
