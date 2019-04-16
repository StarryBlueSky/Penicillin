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

import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.entities.MediaEntity
import java.util.*

/**
 * Parsed status id object for "id".
 */
val Status.idObj: StatusID
    get() = StatusID(id)

/**
 * Parsed status id object for "in_reply_to_status_id".
 */
val Status.inReplyToStatusIdObj: StatusID?
    get() = inReplyToStatusId?.let { StatusID(it) }

/**
 * Parsed status id object for "quoted_status_id.
 */
val Status.quotedStatusIdObj: StatusID?
    get() = quotedStatusId?.let { StatusID(it) }

/**
 * Parsed status id object for "source_status_id".
 */
val MediaEntity.sourceStatusIdObj: StatusID?
    get() = sourceStatusId?.let { StatusID(it) }

/**
 * Represents numerical status id.
 */
data class StatusID(
    /**
     * Original numerical status id.
     */
    val value: Long
) {
    companion object {
        private const val magicNumber = 1288834974657L
    }

    /**
     * Epoch time in milliseconds.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    val epochTimeMillis: Long
        get() = (value shr 22) + magicNumber

    /**
     * New [Date] instance with epoch time.
     */
    val date: Date
        get() = Date(epochTimeMillis)

    /**
     * New [Calendar] instance with epoch time.
     */
    val calendar: Calendar
        get() =  Calendar.getInstance().also {
            it.time = date
        }
}
