@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.models.MediaEntity
import jp.nephy.penicillin.models.Status
import java.util.*

val Status.idObj: StatusID
    get() = id.asStatusID()

val Status.inReplyToStatusIdObj: StatusID?
    get() = inReplyToStatusId?.asStatusID()

val Status.quotedStatusIdObj: StatusID?
    get() = quotedStatusId?.asStatusID()

val MediaEntity.sourceStatusIdObj: StatusID?
    get() = sourceStatusId?.asStatusID()

data class StatusID(val value: Long) {
    companion object {
        private const val magicNumber = 1288834974657
    }

    val epochTimeMillis: Long
        get() = (value shr 22) + magicNumber

    fun toDate(): Date {
        return Date(epochTimeMillis)
    }

    fun toCalendar(): Calendar {
        return Calendar.getInstance().also {
            it.timeInMillis = epochTimeMillis
        }
    }
}

fun Long.asStatusID(): StatusID {
    return StatusID(this)
}
