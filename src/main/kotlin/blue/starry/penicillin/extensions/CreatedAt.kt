/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

package blue.starry.penicillin.extensions

import blue.starry.penicillin.models.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Parsed "created_at" object.
 */
val ActivityEvent.createdAt: CreatedAt
    get() = CreatedAt(createdAtRaw)

/**
 * Parsed "created_at" object.
 */
val DirectMessage.createdAt: CreatedAt
    get() = CreatedAt(createdAtRaw)

/**
 * Parsed "lastPublishTime" object.
 */
val Moment.lastPublishTime: CreatedAt
    get() = CreatedAt(lastPublishTimeRaw)

/**
 * Parsed "created_at" object.
 */
val SavedSearch.createdAt: CreatedAt
    get() = CreatedAt(createdAtRaw)

/**
 * Parsed "created_at" object.
 */
val Status.createdAt: CreatedAt
    get() = CreatedAt(createdAtRaw)

/**
 * Parsed "created_at" object.
 */
val TwitterList.createdAt: CreatedAt
    get() = CreatedAt(createdAtRaw)

/**
 * Parsed "created_at" object.
 */
val CommonUser.createdAt: CreatedAt
    get() = CreatedAt(createdAtRaw)

/**
 * Parsed "created_at" object.
 */
val Stream.Event.createdAt: CreatedAt
    get() = CreatedAt(createdAtRaw)

/**
 * Represents "created_at" date format.
 */
data class CreatedAt(
    /**
     * Original "created_at" string.
     */
    val value: String
) {
    companion object {
        private const val pattern = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
    }

    /**
     * New [Date] instance for this "created_at" string.
     */
    @Deprecated("This property will be abolished.", replaceWith = ReplaceWith("instance"))
    val date: Date
        get() = SimpleDateFormat(pattern, Locale.ENGLISH).parse(value)

    /**
     * New [Calendar] instance for this "created_at" string.
     */
    @Suppress("Deprecation")
    @Deprecated("This property will be abolished.", replaceWith = ReplaceWith("instance"))
    val calendar: Calendar
        get() = Calendar.getInstance().also {
            it.time = date
        }
    
    /**
     * New [Instant] instance for this "created_at" string.
     */
    val instant: Instant
        get() = Instant.from(DateTimeFormatter.ofPattern(pattern).parse(value))
}
