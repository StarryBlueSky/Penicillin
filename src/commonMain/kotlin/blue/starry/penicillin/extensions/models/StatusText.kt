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

package blue.starry.penicillin.extensions.models

import blue.starry.penicillin.models.Status

/**
 * Returns full-body status text.
 * Supports both tweet modes (Extend and Compat).
 */
public val Status.text: String
    get () = if (retweetedStatus != null) {
        if (retweetedStatus?.extendedTweet != null) {
            "RT @${retweetedStatus!!.user.screenName}: ${retweetedStatus!!.text}"
        } else {
            fullTextRaw ?: textRaw ?: error("Unsupported status format: $json")
        }
    } else {
        extendedTweet?.fullText ?: fullTextRaw ?: textRaw ?: error("Unsupported status format: $json")
    }.unescapeHTML()

/**
 * Returns full-body status text which shortened urls in are each expanded.
 */
public val Status.expandedText: String
    get() {
        val entities = entities.let { it.media + it.urls }
            .sortedBy { it.firstIndex }
        return buildString {
            entities.fold(0) { acc, entity ->
                val startAt = text.indexOf(entity.url, acc, true).takeUnless { it < 0 } ?: return@fold acc
                append(text.substring(acc, startAt))
                append(entity.expandedUrl)
                startAt + entity.url.length
            }.let {
                append(text.substring(it))
            }
        }
    }

/**
 * Returns full-body status text which shortened urls in are each expanded.
 */
@Deprecated("This function often throws IndexOutOfBoundsException because of incorrect indices of tweet entities.")
public val Status.expandedTextWithIndices: String
    get() {
        val entities = entities.let { it.media + it.urls }
            .sortedBy { it.firstIndex }
        return buildString(text.length + entities.sumOf { it.expandedUrl.length - it.url.length }) {
            entities.fold(0) { acc, entity ->
                append(text.substring(acc, entity.firstIndex))
                append(entity.expandedUrl)
                entity.lastIndex
            }.let {
                append(text.substring(it))
            }
        }
    }

private fun String.unescapeHTML(): String {
    return replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
}
