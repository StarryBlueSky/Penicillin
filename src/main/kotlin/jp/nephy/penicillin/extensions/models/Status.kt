@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions.models

import jp.nephy.penicillin.models.Status

fun Status.fullText(): String {
    return if (retweetedStatus != null) {
        if (retweetedStatus?.extendedTweet != null) {
            "RT @${retweetedStatus!!.user.screenName}: ${retweetedStatus!!.extendedTweet!!.fullText}"
        } else {
            fullText ?: text
        }
    } else {
        extendedTweet?.fullText ?: fullText ?: text
    }
}
