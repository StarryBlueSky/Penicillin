@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.jsonkt.delegation.string
import jp.nephy.penicillin.models.*
import java.text.SimpleDateFormat
import java.util.*

val ActivityEvent.createdAt: CreatedAt
    get() {
        val value by string("created_at")
        return value.asCreatedAt()
    }

val DirectMessage.createdAt: CreatedAt
    get() {
        val value by string("created_at")
        return value.asCreatedAt()
    }

val Moment.lastPublishTime: CreatedAt
    get() {
        val value by string("lastPublishTime")
        return value.asCreatedAt()
    }

val SavedSearch.createdAt: CreatedAt
    get() {
        val value by string("created_at")
        return value.asCreatedAt()
    }

val Status.createdAt: CreatedAt
    get() {
        val value by string("created_at")
        return value.asCreatedAt()
    }

val TwitterList.createdAt: CreatedAt
    get() {
        val value by string("created_at")
        return value.asCreatedAt()
    }

val CommonUser.createdAt: CreatedAt
    get() {
        val value by string("created_at")
        return value.asCreatedAt()
    }

val UserStream.Event.createdAt: CreatedAt
    get() {
        val value by string("created_at")
        return value.asCreatedAt()
    }

data class CreatedAt(val value: String) {
    companion object {
        private const val pattern = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
    }

    val date: Date
        get() = SimpleDateFormat(pattern, Locale.ENGLISH).parse(value)
    val calendar: Calendar
        get() = Calendar.getInstance().also {
            it.timeInMillis = date.time
        }
}

fun String.asCreatedAt(): CreatedAt {
    return CreatedAt(this)
}
