package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.models.Status

val Status.likeCount: Int
    get() = favoriteCount

val Status.liked: Boolean
    get() = favorited
