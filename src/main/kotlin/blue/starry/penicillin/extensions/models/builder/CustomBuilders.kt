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

import blue.starry.penicillin.core.streaming.handler.UserStreamEvent
import blue.starry.penicillin.core.streaming.handler.UserStreamEventType
import blue.starry.penicillin.models.*

/**
 * Creates new custom [Status] object.
 */
inline fun newStatus(builder: CustomStatusBuilder.() -> Unit): Status {
    return CustomStatusBuilder().apply(builder).build()
}

/**
 * Creates new custom [Stream.StatusEvent] object.
 */
inline fun newStatusEvent(event: UserStreamEvent, builder: CustomStatusEventBuilder.() -> Unit): Stream.StatusEvent {
    require(event.type == UserStreamEventType.Status)
    return CustomStatusEventBuilder(event).apply(builder).build()
}

/**
 * Creates new custom [TwitterList] object.
 */
inline fun newList(builder: CustomListBuilder.() -> Unit): TwitterList {
    return CustomListBuilder().apply(builder).build()
}

/**
 * Creates new custom [Stream.ListEvent] object.
 */
inline fun newListEvent(event: UserStreamEvent, builder: CustomListEventBuilder.() -> Unit): Stream.ListEvent {
    require(event.type == UserStreamEventType.List)
    return CustomListEventBuilder(event).apply(builder).build()
}

/**
 * Creates new custom [User] object.
 */
inline fun newUser(builder: CustomUserBuilder.() -> Unit): User {
    return CustomUserBuilder().apply(builder).build()
}

/**
 * Creates new custom [Stream.UserEvent] object.
 */
inline fun newUserEvent(event: UserStreamEvent, builder: CustomUserEventBuilder.() -> Unit): Stream.UserEvent {
    require(event.type == UserStreamEventType.User)
    return CustomUserEventBuilder(event).apply(builder).build()
}

/**
 * Creates new custom [DirectMessage] object.
 */
inline fun newDirectMessage(builder: CustomDirectMessageBuilder.() -> Unit): DirectMessage {
    return CustomDirectMessageBuilder().apply(builder).build()
}

/**
 * Creates new custom [Stream.Delete] object.
 */
inline fun newDelete(builder: CustomDeleteBuilder.() -> Unit): Stream.Delete {
    return CustomDeleteBuilder().apply(builder).build()
}
