/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.models.entities.MediaEntity
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

    @Suppress("MemberVisibilityCanBePrivate")
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
