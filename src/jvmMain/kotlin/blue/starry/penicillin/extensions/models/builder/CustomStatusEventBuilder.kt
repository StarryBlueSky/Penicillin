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

package blue.starry.penicillin.extensions.models.builder

import blue.starry.jsonkt.parseObject
import blue.starry.jsonkt.toJsonObject
import blue.starry.penicillin.core.experimental.PenicillinExperimentalApi
import blue.starry.penicillin.core.session.NoopApiClient
import blue.starry.penicillin.core.streaming.handler.UserStreamEvent

import blue.starry.penicillin.models.Stream
import java.time.temporal.TemporalAccessor
import kotlin.collections.set

/**
 * Custom payload builder for [Stream.StatusEvent].
 */
public class CustomStatusEventBuilder(type: UserStreamEvent): JsonBuilder<Stream.StatusEvent>, JsonMap by jsonMapOf(
    "event" to type.key,
    "source" to null,
    "target" to null,
    "target_object" to null,
    "created_at" to null
) {
    private var source = CustomUserBuilder()
    /**
     * Sets source.
     */
    public fun source(builder: CustomUserBuilder.() -> Unit) {
        source.apply(builder)
    }

    private var target = CustomUserBuilder()
    /**
     * Sets target.
     */
    public fun target(builder: CustomUserBuilder.() -> Unit) {
        target.apply(builder)
    }

    private var targetObject = CustomStatusBuilder()
    /**
     * Sets target_object.
     */
    public fun targetObject(builder: CustomStatusBuilder.() -> Unit) {
        targetObject.apply(builder)
    }

    /**
     * "created_at".
     */
    public var createdAt: TemporalAccessor? = null

    @OptIn(PenicillinExperimentalApi::class)
    override fun build(): Stream.StatusEvent {
        val source = source.build()
        val target = target.build()
        val targetObject = targetObject.build()
        
        this["source"] = source
        this["target"] = target
        this["target_object"] = targetObject
        this["created_at"] = createdAt.toCreatedAt()

        return toJsonObject().parseObject { Stream.StatusEvent(it, NoopApiClient) }
    }
}
