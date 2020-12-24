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

import blue.starry.penicillin.core.response.ApiResponse

/**
 * Set of "x-access-level" header.
 * If this header is absent or unknown, returns empty set.
 */
public val ApiResponse<*>.accessLevels: Set<AccessLevel>
    get() = when (response.headers["x-access-level"]?.toLowerCase()) {
        "read" -> setOf(AccessLevel.Read)
        "read-write" -> setOf(AccessLevel.Read, AccessLevel.Write)
        "read-write-directmessages" -> setOf(AccessLevel.Read, AccessLevel.Write, AccessLevel.DirectMessages)
        else -> emptySet()
    }

/**
 * Checks if this application has "read" permission.
 */
public val ApiResponse<*>.hasReadPermission: Boolean
    get() = AccessLevel.Read in accessLevels

/**
 * Checks if this application has "write" permission.
 */
public val ApiResponse<*>.hasWritePermission: Boolean
    get() = AccessLevel.Write in accessLevels

/**
 * Checks if this application has "direct messages" permission.
 */
public val ApiResponse<*>.hasDirectMessagesPermission: Boolean
    get() = AccessLevel.DirectMessages in accessLevels

/**
 * Represents "x-access-level" header.
 */
public enum class AccessLevel(private val identifier: String) {
    /**
     * Indicates this application has "read" permission.
     */
    Read("read"),

    /**
     * Indicates this application has "write" permission.
     */
    Write("read-write"),

    /**
     * Indicates this application has "direct messages" permission.
     */
    DirectMessages("read-write-directmessages")
}
