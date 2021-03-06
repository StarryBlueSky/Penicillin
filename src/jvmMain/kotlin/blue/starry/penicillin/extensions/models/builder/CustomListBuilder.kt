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
import blue.starry.penicillin.core.session.NoopApiClient
import blue.starry.penicillin.models.TwitterList
import java.time.temporal.TemporalAccessor
import kotlin.collections.set

/**
 * Custom payload builder for [TwitterList].
 */
public class CustomListBuilder: JsonBuilder<TwitterList>, JsonMap by jsonMapOf(
    "created_at" to null,
    "description" to "Tweetstorm",
    "following" to false,
    "full_name" to "Tweetstorm",
    "id" to null,
    "id_str" to null,
    "member_count" to 0,
    "mode" to "public",
    "name" to "Tweetstorm",
    "slag" to "Tweetstorm",
    "subscriber_count" to 0,
    "uri" to "Tweetstorm/Tweetstorm"
) {
    /**
     * "created_at".
     */
    public var createdAt: TemporalAccessor? = null

    private var description: String? = null
    /**
     * Sets description.
     */
    public fun description(text: () -> Any?) {
        description = text()?.toString().orEmpty()
    }

    private var following = false
    /**
     * Sets following.
     */
    public fun following() {
        following = true
    }

    private lateinit var listName: String
    private lateinit var listFullName: String
    private lateinit var listSlug: String
    private lateinit var listUri: String
    /**
     * Sets name.
     */
    public fun name(shortName: String, fullName: String, slug: String, uri: String) {
        listName= shortName
        listFullName = fullName
        listSlug = slug
        listUri = uri
    }

    private var memberCount = 0
    private var subscriberCount = 0
    /**
     * Sets count.
     */
    public fun count(member: Int = 0, subscriber: Int = 0) {
        memberCount = member
        subscriberCount = subscriber
    }

    override fun build(): TwitterList {
        val id = generateId()
        
        this["name"] = listName
        this["full_name"] = listFullName
        this["slug"] = listSlug
        this["uri"] = listUri

        this["description"] = description
        this["following"] = following

        this["created_at"] = createdAt.toCreatedAt()

        this["member_count"] = memberCount
        this["subscriber_count"] = subscriberCount

        this["id"] = id
        this["id_str"] = id.toString()

        return toJsonObject().parseObject { TwitterList(it, NoopApiClient) }
    }
}
