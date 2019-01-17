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
