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

package tests

import blue.starry.penicillin.endpoints.statuses
import blue.starry.penicillin.endpoints.statuses.create
import blue.starry.penicillin.endpoints.timeline
import blue.starry.penicillin.endpoints.timeline.homeTimeline
import blue.starry.penicillin.extensions.executeAsync
import blue.starry.penicillin.extensions.models.text
import blue.starry.penicillin.extensions.queue
import kotlinx.coroutines.asPromise
import kotlin.random.Random
import kotlin.random.nextULong

actual fun testTimeline() {
    TEST_CLIENT.timeline.homeTimeline.queue { timeline ->
        timeline.forEach {
            println(it.text)
        }
    }
}

@ExperimentalUnsignedTypes
actual fun testTweet() {
    TEST_CLIENT.statuses.create("This is test tweet from Penicillin on Kotlin/JS! 0x${Random.nextULong().toString(16)}").queue()
}
