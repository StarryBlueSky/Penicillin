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

package blue.starry.penicillin.extensions.models.builder

import blue.starry.jsonkt.jsonObjectOf
import blue.starry.jsonkt.parseObject
import blue.starry.jsonkt.toJsonObject
import blue.starry.penicillin.core.session.NoopApiClient
import blue.starry.penicillin.models.Stream
import java.time.Instant
import java.time.temporal.TemporalAccessor
import kotlin.collections.set
import kotlin.properties.Delegates

/**
 * Custom payload builder for [Stream.Delete].
 */
class CustomDeleteBuilder: JsonBuilder<Stream.Delete>, JsonMap by jsonMapOf(
    "delete" to jsonObjectOf(
        "status" to jsonObjectOf(
            "id" to null,
            "id_str" to null,
            "user_id" to null,
            "user_id_str" to null
        ),
        "timestamp_ms" to null
    )
) {
    private var statusId by Delegates.notNull<Long>()
    /**
     * Sets status id.
     */
    fun status(id: Long) {
        statusId = id
    }

    private var userId by Delegates.notNull<Long>()
    /**
     * Sets author user id.
     */
    fun author(id: Long) {
        userId = id
    }

    /**
     * timestamp.
     */
    var timestamp: TemporalAccessor? = null

    override fun build(): Stream.Delete {
        this["delete"] = jsonObjectOf(
            "status" to jsonObjectOf(
                "id" to statusId,
                "id_str" to statusId.toString(),
                "user_id" to userId,
                "user_id_str" to userId.toString()
            ),
            "timestamp_ms" to (Instant.from(timestamp) ?: Instant.now()).toEpochMilli().toString()
        )
        
        return toJsonObject().parseObject { Stream.Delete(it, NoopApiClient) }
    }
}
