@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.jsonkt.toJsonString
import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.request.action.JoinedJsonObjectActions
import jp.nephy.penicillin.core.request.action.PenicillinMultipleJsonObjectActions
import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.extensions.filter
import jp.nephy.penicillin.extensions.join
import jp.nephy.penicillin.endpoints.parameters.EmbedAlign
import jp.nephy.penicillin.endpoints.parameters.EmbedWidgetType
import jp.nephy.penicillin.endpoints.parameters.MediaDataComponent
import jp.nephy.penicillin.endpoints.parameters.MediaFileComponent
import jp.nephy.penicillin.models.*
import jp.nephy.penicillin.models.Card
import jp.nephy.penicillin.models.Media
import jp.nephy.penicillin.models.Status
import java.util.concurrent.TimeUnit

class Statuses(override val client: PenicillinClient): Endpoint {
    fun show(id: Long, trimUser: Boolean? = null, includeMyRetweet: Boolean? = null, includeEntities: Boolean? = null, includeExtAltText: Boolean? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/statuses/show.json") {
            parameter("id" to id, "trim_user" to trimUser, "include_my_retweet" to includeMyRetweet, "include_entities" to includeEntities, "include_ext_alt_text" to includeExtAltText, *options)
        }.jsonObject<Status>()

    fun lookup(
        id: kotlin.collections.List<Long>, trimUser: Boolean? = null, map: Boolean? = null, includeEntities: Boolean? = null, includeExtAltText: Boolean? = null, vararg options: Pair<String, Any?>
    ) = client.session.get("/1.1/statuses/lookup.json") {
        parameter("id" to id.joinToString(","), "trim_user" to trimUser, "map" to map, "include_entities" to includeEntities, "include_ext_alt_text" to includeExtAltText, *options)
    }.jsonArray<Status>()

    fun retweeterIds(id: Long, stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/statuses/retweeters/ids.json") {
        parameter("id" to id, "stringify_ids" to stringifyIds, *options)
    }.cursorJsonObject<CursorIds>()

    fun retweets(id: Long, count: Int? = null, trimUser: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/statuses/retweets/$id.json") {
        parameter("count" to count, "trim_user" to trimUser, *options)
    }.jsonArray<Status>()

    fun retweetsOfMe(
        count: Int? = null,
        sinceId: Long? = null,
        maxId: Long? = null,
        trimUser: Boolean? = null,
        includeEntities: Boolean? = null,
        includeUserEntities: Boolean? = null,
        vararg options: Pair<String, Any?>
    ) = client.session.get("/1.1/statuses/retweets_of_me.json") {
        parameter("count" to count, "since_id" to sinceId, "max_id" to maxId, "trim_user" to trimUser, "include_entities" to includeEntities, "include_user_entities" to includeUserEntities, *options)
    }.jsonArray<Status>()

    fun embedFormat(
        url: String,
        maxwidth: Int? = null,
        hideMedia: Boolean? = null,
        hideThread: Boolean? = null,
        omitScript: Boolean? = null,
        align: EmbedAlign? = null,
        related: kotlin.collections.List<String>? = null,
        lang: String? = null,
        theme: String? = null,
        linkColor: String? = null,
        widgetType: EmbedWidgetType? = null,
        dnt: Boolean? = null,
        vararg options: Pair<String, Any?>
    ) = client.session.get("/oembed", EndpointHost.Publish) {
        authType(AuthorizationType.None)
        parameter(
            "url" to url,
            "maxwidth" to maxwidth,
            "hide_media" to hideMedia,
            "hide_thread" to hideThread,
            "omit_script" to omitScript,
            "align" to align?.value,
            "related" to related?.joinToString(","),
            "lang" to lang,
            "theme" to theme,
            "link_color" to linkColor,
            "widget_type" to widgetType?.value,
            "dnt" to dnt,
            *options
        )
    }.jsonObject<Embed>()

    fun update(
        status: String,
        inReplyToStatusId: Long? = null,
        possiblySensitive: Boolean? = null,
        lat: Float? = null,
        long: Float? = null,
        placeId: String? = null,
        displayCoordinates: Boolean? = null,
        trimUser: Boolean? = null,
        mediaIds: List<Long>? = null,
        enableDMCommands: Boolean? = null,
        failDMCommands: Boolean? = null,
        cardUri: String? = null,
        vararg options: Pair<String, Any?>
    ) = client.session.post("/1.1/statuses/update.json") {
        body {
            form {
                add(
                    "auto_populate_reply_metadata" to "true",
                    "batch_mode" to "off",
                    "cards_platform" to "iPhone-13",
                    "contributor_details" to "1",
                    "enable_dm_commands" to "false",
                    "ext" to "altText,highlightedLabel,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo",
                    "include_cards" to "1",
                    "include_carousels" to "1",
                    "include_entities" to "1",
                    "include_ext_media_color" to "true",
                    "include_media_features" to "true",
                    "include_my_retweet" to "1",
                    "include_profile_interstitial_type" to "true",
                    "include_profile_location" to "true",
                    "include_reply_count" to "1",
                    "include_user_entities" to "true",
                    "include_user_hashtag_entities" to "true",
                    "include_user_mention_entities" to "true",
                    "include_user_symbol_entities" to "true",
                    emulationMode = EmulationMode.TwitterForiPhone
                )
                add(
                    "status" to status,
                    "card_uri" to cardUri,
                    "in_reply_to_status_id" to inReplyToStatusId,
                    "possibly_sensitive" to possiblySensitive,
                    "lat" to lat,
                    "long" to long,
                    "place_id" to placeId,
                    "display_coordinates" to displayCoordinates,
                    "trim_user" to trimUser,
                    "media_ids" to mediaIds?.joinToString(","),
                    "enable_dm_commands" to enableDMCommands,
                    "fail_dm_commands" to failDMCommands
                )
                add("tweet_mode" to "extended", emulationMode = EmulationMode.TwitterForiPhone)
                add(*options)
            }
        }
    }.jsonObject<Status>()

    fun delete(id: Long, trimUser: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/statuses/destroy/$id.json") {
        body {
            form {
                add("trim_user" to trimUser, *options)
            }
        }
    }.jsonObject<Status>()

    fun retweet(id: Long, trimUser: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/statuses/retweet/$id.json") {
        body {
            form {
                add("trim_user" to trimUser, *options)
            }
        }
    }.jsonObject<Status>()

    fun unretweet(id: Long, trimUser: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/statuses/unretweet/$id.json") {
        body {
            form {
                add("trim_user" to trimUser, *options)
            }
        }
    }.jsonObject<Status>()

    fun updateWithMediaFile(status: String, media: List<MediaFileComponent>, waitSec: Long? = null, vararg options: Pair<String, Any?>): JoinedJsonObjectActions<Media, Status> {
        return media.map {
            client.media.uploadMedia(it.file, it.type, it.category)
        }.join { results ->
            if (waitSec != null) {
                // TODO: wait until media process completes
                TimeUnit.SECONDS.sleep(waitSec)
            }

            update(status, mediaIds = results.asSequence().map { it.filter<Media>().first() }.map { it.result.mediaId }.toList(), options = *options)
        }
    }

    fun updateWithMedia(status: String, media: List<MediaDataComponent>, waitSec: Long? = null, vararg options: Pair<String, Any?>): JoinedJsonObjectActions<Media, Status> {
        return media.map {
            client.media.uploadMedia(it.data, it.type, it.category)
        }.join { results ->
            val result = results.map { it.filter<Media>().first() }.first()
            val secs = waitSec ?: result.result.processingInfo.checkAfterSecs?.toLong()?.times(1000)
            if (secs != null) {
                TimeUnit.SECONDS.sleep(secs)
            }

            update(status, mediaIds = listOf(result.result.mediaId), options = *options)
        }
    }

    @PrivateEndpoint(EmulationMode.TwitterForiPhone)
    fun createPollTweet(status: String, choices: List<String>, minutes: Int = 1440, vararg options: Pair<String, Any?>): PenicillinMultipleJsonObjectActions<Card> {
        return PenicillinMultipleJsonObjectActions.Builder {
            client.card.create(
                cardData = linkedMapOf<String, Any>().apply {
                    choices.forEachIndexed { i, choice ->
                        put("twitter:string:choice${i + 1}_label", choice)
                    }
                    put("twitter:api:api:endpoint", "1")
                    put("twitter:card", "poll${choices.size}choice_text_only")
                    put("twitter:long:duration_minutes", minutes)
                }.toJsonString()
            )
        }.request {
            update(status, cardUri = it.first.result.cardUri, options = *options)
        }.build()
    }

    @PrivateEndpoint
    fun pin(id: Long, vararg options: Pair<String, Any?>) = client.session.post("/1.1/account/pin_tweet.json") {
        body {
            form {
                add("id" to id, *options)
            }
        }
    }.jsonObject<PinTweet>()

    @PrivateEndpoint
    fun unpin(id: Long, vararg options: Pair<String, Any?>) = client.session.post("/1.1/account/unpin_tweet.json") {
        body {
            form {
                add("id" to id, *options)
            }
        }
    }.jsonObject<PinTweet>()
}
