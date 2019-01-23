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

package jp.nephy.penicillin.extensions.models.builder

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.streaming.handler.UserStreamEvent
import jp.nephy.penicillin.core.streaming.handler.UserStreamEventType
import jp.nephy.penicillin.models.*

fun PenicillinClient.newStatus(builder: CustomStatusBuilder.() -> Unit): Status {
    return CustomStatusBuilder(this).apply(builder).build()
}

fun PenicillinClient.newStatusEvent(event: UserStreamEvent, builder: CustomStatusEventBuilder.() -> Unit): UserStream.StatusEvent {
    require(event.type == UserStreamEventType.Status)
    return CustomStatusEventBuilder(this, event).apply(builder).build()
}

fun PenicillinClient.newList(builder: CustomListBuilder.() -> Unit): TwitterList {
    return CustomListBuilder(this).apply(builder).build()
}

fun PenicillinClient.newListEvent(event: UserStreamEvent, builder: CustomListEventBuilder.() -> Unit): UserStream.ListEvent {
    require(event.type == UserStreamEventType.List)
    return CustomListEventBuilder(this, event).apply(builder).build()
}

fun PenicillinClient.newUser(builder: CustomUserBuilder.() -> Unit): User {
    return CustomUserBuilder(this).apply(builder).build()
}

fun PenicillinClient.newUserEvent(event: UserStreamEvent, builder: CustomUserEventBuilder.() -> Unit): UserStream.UserEvent {
    require(event.type == UserStreamEventType.User)
    return CustomUserEventBuilder(this, event).apply(builder).build()
}

fun PenicillinClient.newDirectMessage(builder: CustomDirectMessageBuilder.() -> Unit): DirectMessage {
    return CustomDirectMessageBuilder(this).apply(builder).build()
}

fun PenicillinClient.newDelete(builder: CustomDeleteBuilder.() -> Unit): Stream.Delete {
    return CustomDeleteBuilder(this).apply(builder).build()
}
