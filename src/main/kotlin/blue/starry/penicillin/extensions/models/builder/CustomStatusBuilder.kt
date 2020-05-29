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

import blue.starry.jsonkt.*
import blue.starry.penicillin.core.session.NoopApiClient
import blue.starry.penicillin.models.Status
import kotlinx.atomicfu.atomic
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*
import kotlin.collections.set

/**
 * Custom payload builder for [Status].
 */
class CustomStatusBuilder: JsonBuilder<Status>, JsonMap by jsonMapOf(
    "created_at" to null,
    "id" to null,
    "id_str" to null,
    "text" to "",
    "source" to null,
    "truncated" to false,
    "in_reply_to_status_id" to null,
    "in_reply_to_status_id_str" to null,
    "in_reply_to_user_id" to null,
    "in_reply_to_user_id_str" to null,
    "in_reply_to_screen_name" to null,
    "user" to null,
    "geo" to null,
    "coordinates" to null,
    "place" to null,
    "contributors" to null,
    "is_quote_status" to false,
    "quote_count" to 0,
    "reply_count" to 0,
    "retweet_count" to 0,
    "favorite_count" to 0,
    "entities" to jsonObjectOf(
        "hashtags" to jsonArrayOf(), "symbols" to jsonArrayOf(), "user_mentions" to jsonArrayOf(), "urls" to jsonArrayOf()
    ),
    "favorited" to false,
    "retweeted" to false,
    "filter_level" to "low",
    "lang" to "ja",
    "timestamp_ms" to null
) {
    private lateinit var text: String
    /**
     * Sets text.
     */
    fun text(value: String) {
        text = value
    }

    /**
     * Sets text.
     */
    fun text(operation: () -> Any?) {
        text = operation.invoke().toString()
    }

    /**
     * Sets text with builder.
     */
    fun textBuilder(builder: StringBuilder.() -> Unit) {
        text = buildString(builder)
    }

    private var sourceName = "Tweetstorm"
    private var sourceUrl = "https://github.com/SlashNephy/Tweetstorm"
    /**
     * Sets source.
     */
    fun source(name: String, url: String) {
        sourceName = name
        sourceUrl = url
    }

    private var createdAt: TemporalAccessor? = null
    /**
     * Sets created_at.
     */
    fun createdAt(time: TemporalAccessor? = null) {
        createdAt = time
    }

    private var user = CustomUserBuilder()
    /**
     * Sets user.
     */
    fun user(builder: CustomUserBuilder.() -> Unit) {
        user.apply(builder)
    }

    private var inReplyToStatusId: Long? = null
    private var inReplyToUserId: Long? = null
    private var inReplyToScreenName: String? = null
    /**
     * Sets in_reply_to.
     */
    fun inReplyTo(statusId: Long, userId: Long, screenName: String) {
        inReplyToStatusId = statusId
        inReplyToUserId = userId
        inReplyToScreenName = screenName
    }

    private var retweeted = false
    /**
     * Sets retweeted.
     */
    fun alreadyRetweeted() {
        retweeted = true
    }

    private var favorited = false
    /**
     * Sets favorited.
     */
    fun alreadyFavorited() {
        favorited = true
    }

    private var retweetCount = 0
    private var favoriteCount = 0
    /**
     * Sets count.
     */
    fun count(retweet: Int = 0, favorite: Int = 0) {
        retweetCount = retweet
        favoriteCount = favorite
    }

    private data class UrlEntity(val url: String, val start: Int, val end: Int)

    private val urls = mutableListOf<UrlEntity>()
    /**
     * Sets url.
     */
    fun url(url: String, start: Int, end: Int) {
        urls += UrlEntity(url, start, end)
    }

    override fun build(): Status {
        val id = generateId()
        val user = user.build()
    
        this["text"] = text

        this["id"] = id
        this["id_str"] = id.toString()

        this["source"] = "<a href=\"$sourceUrl\" rel=\"nofollow\">$sourceName</a>"

        this["user"] = user

        this["created_at"] = createdAt.toCreatedAt()
        this["timestamp_ms"] = (Instant.from(createdAt) ?: Instant.now()).toEpochMilli().toString()

        this["retweeted"] = retweeted
        this["favorited"] = favorited
        this["retweet_count"] = retweetCount
        this["favorite_count"] = favoriteCount

        this["in_reply_to_status_id"] = inReplyToStatusId
        this["in_reply_to_status_id_str"] = inReplyToStatusId.toString()
        this["in_reply_to_user_id"] = inReplyToUserId
        this["in_reply_to_user_id_str"] = inReplyToUserId.toString()
        this["in_reply_to_screen_name"] = inReplyToScreenName

        val entities = this["entities"].asJsonElement().jsonObject

        this["entities"] = entities.copy { entity ->
            entity["urls"] = urls.map { urlEntity ->
                jsonObjectOf(
                    "display_url" to urlEntity.url.removePrefix("https://").removePrefix("http://"),
                    "url" to urlEntity.url,
                    "indices" to jsonArrayOf(urlEntity.start, urlEntity.end),
                    "expanded_url" to urlEntity.url
                )
            }
        }

        return toJsonObject().parseObject { Status(it, NoopApiClient) }
    }
}

internal fun TemporalAccessor?.toCreatedAt(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH).withZone(ZoneId.of("UTC"))
    return dateFormatter.format(this ?: Instant.now())
}

private var id = atomic(100000001L)
internal fun generateId(): Long {
    return id.also {
        id.plusAssign(2)
    }.value
}
