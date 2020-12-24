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

@file:Suppress("Unused")

package blue.starry.penicillin.extensions

import blue.starry.penicillin.endpoints.media.MediaCategory
import blue.starry.penicillin.endpoints.media.MediaComponent
import blue.starry.penicillin.endpoints.media.MediaType
import java.io.File
import java.nio.file.Path
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("FunctionName")
public fun MediaComponent(file: File, type: MediaType, category: MediaCategory = MediaCategory.TweetImage): MediaComponent {
    return MediaComponent(file.readBytes(), type, category)
}

@Suppress("FunctionName")
public fun MediaComponent(path: Path, type: MediaType, category: MediaCategory = MediaCategory.TweetImage): MediaComponent {
    return MediaComponent(path.toFile(), type, category)
}

/**
 * New [Instant] instance for this "created_at" string.
 */
public val CreatedAt.instant: Instant
    get() = Instant.from(
        DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss X uuuu", Locale.ROOT).parse(value)
    )

/**
 * New [Locale] instance for this "lang" value.
 */
public val Language.locale: Locale
    get() = Locale(value)

/**
 * New [Instant] instance with epoch time.
 */
public val StatusID.instant: Instant
    get() = Instant.ofEpochMilli(epochTimeMillis)
