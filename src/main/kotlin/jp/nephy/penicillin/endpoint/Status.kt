package jp.nephy.penicillin.endpoint

import jp.nephy.jsonkt.JsonKt
import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoint.parameter.EmbedAlign
import jp.nephy.penicillin.endpoint.parameter.EmbedWidgetType
import jp.nephy.penicillin.endpoint.parameter.MediaCategory
import jp.nephy.penicillin.endpoint.parameter.MediaType
import jp.nephy.penicillin.model.CursorIds
import jp.nephy.penicillin.model.Embed
import jp.nephy.penicillin.model.PinTweet
import jp.nephy.penicillin.model.Status
import jp.nephy.penicillin.request.AuthorizationType
import jp.nephy.penicillin.request.ObjectAction
import java.io.File


class Status(override val client: PenicillinClient): Endpoint {
    fun show(id: Long, trimUser: Boolean? = null, includeMyRetweet: Boolean? = null, includeEntities: Boolean? = null, includeExtAltText: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getObject<Status>("/statuses/show.json") {
        query("id" to id, "trim_user" to trimUser, "include_my_retweet" to includeMyRetweet, "include_entities" to includeEntities, "include_ext_alt_text" to includeExtAltText, *options)
    }

    fun lookup(id: kotlin.collections.List<Long>, trimUser: Boolean? = null, map: Boolean? = null, includeEntities: Boolean? = null, includeExtAltText: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<Status>("/statuses/lookup.json") {
        query("id" to id.joinToString(","), "trim_user" to trimUser, "map" to map, "include_entities" to includeEntities, "include_ext_alt_text" to includeExtAltText, *options)
    }

    fun retweeterIds(id: Long, stringifyIds: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getCursorObject<CursorIds>("/statuses/retweeters/ids.json") {
        query("id" to id, "stringify_ids" to stringifyIds, *options)
    }

    fun retweets(id: Long, count: Int? = null, trimUser: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<Status>("/statuses/retweets/$id.json") {
        query("count" to count, "trim_user" to trimUser, *options)
    }

    fun retweetsOfMe(count: Int? = null, sinceId: Long? = null, maxId: Long? = null, trimUser: Boolean? = null, includeEntities: Boolean? = null, includeUserEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<Status>("/statuses/retweets_of_me.json") {
        query("count" to count, "since_id" to sinceId, "max_id" to maxId, "trim_user" to trimUser, "include_entities" to includeEntities, "include_user_entities" to includeUserEntities, *options)
    }

    fun embedFormat(url: String, maxwidth: Int? = null, hideMedia: Boolean? = null, hideThread: Boolean? = null, omitScript: Boolean? = null, align: EmbedAlign? = null, related: kotlin.collections.List<String>? = null, lang: String? = null, theme: String? = null, linkColor: String? = null, widgetType: EmbedWidgetType? = null, dnt: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getObject<Embed>("https://publish.twitter.com/oembed", AuthorizationType.None) {
        query("url" to url, "maxwidth" to maxwidth, "hide_media" to hideMedia, "hide_thread" to hideThread, "omit_script" to omitScript, "align" to align?.value, "related" to related?.joinToString(","), "lang" to lang, "theme" to theme, "link_color" to linkColor, "widget_type" to widgetType?.value, "dnt" to dnt, *options)
    }

    fun update(status: String, inReplyToStatusId: Long? = null, possiblySensitive: Boolean? = null, lat: Float? = null, long: Float? = null, placeId: String? = null, displayCoordinates: Boolean? = null, trimUser: Boolean? = null, mediaIds: List<Long>? = null, enableDMCommands: Boolean? = null, failDMCommands: Boolean? = null, cardUri: String? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Status>("/statuses/update.json") {
        form("auto_populate_reply_metadata" to "true",
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
                "include_user_symbol_entities" to "true", onlyOfficialClient = true
        )
        form("status" to status, "card_uri" to cardUri, "in_reply_to_status_id" to inReplyToStatusId, "possibly_sensitive" to possiblySensitive, "lat" to lat, "long" to long, "place_id" to placeId, "display_coordinates" to displayCoordinates, "trim_user" to trimUser, "media_ids" to mediaIds?.joinToString(","), "enable_dm_commands" to enableDMCommands, "fail_dm_commands" to failDMCommands)
        form("tweet_mode" to "extended", onlyOfficialClient = true)
        form(*options)
    }

    fun delete(id: Long, trimUser: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Status>("/statuses/destroy/$id.json") {
        form("trim_user" to trimUser, *options)
    }

    fun retweet(id: Long, trimUser: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Status>("/statuses/retweet/$id.json") {
        form("trim_user" to trimUser, *options)
    }

    fun unretweet(id: Long, trimUser: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Status>("/statuses/unretweet/$id.json") {
        form("trim_user" to trimUser, *options)
    }

    fun updateWithMediaFile(status: String, media: List<Pair<File, MediaType>>, vararg options: Pair<String, Any?>): ObjectAction<Status> {
        val mediaIds = media.map {
            client.media.uploadMediaFile(it.first, it.second, MediaCategory.TweetImage).complete().result.mediaId
        }

        return update(status, mediaIds = mediaIds, options = *options)
    }

    fun updateWithMedia(status: String, media: List<Pair<ByteArray, MediaType>>, vararg options: Pair<String, Any?>): ObjectAction<Status> {
        val mediaIds = media.map {
            client.media.uploadMedia(it.first, it.second, MediaCategory.TweetImage).complete().result.mediaId
        }

        return update(status, mediaIds = mediaIds, options = *options)
    }

    fun createPollTweet(status: String, choices: List<String>, minutes: Int = 1440, vararg options: Pair<String, Any?>): ObjectAction<Status> {
        val card = client.card.create(
                cardData = JsonKt.toJsonString(linkedMapOf<String, Any>().apply {
                    choices.forEachIndexed { i, choice ->
                        put("twitter:string:choice${i + 1}_label", choice)
                    }
                    put("twitter:api:api:endpoint", "1")
                    put("twitter:card", "poll${choices.size}choice_text_only")
                    put("twitter:long:duration_minutes", minutes)
                })
        ).complete()

        return update(status, cardUri = card.result.cardUri, options = *options)
    }

    @PrivateEndpoint
    fun pin(id: Long, vararg options: Pair<String, Any?>) = client.session.postObject<PinTweet>("/account/pin_tweet.json") {
        form("id" to id, *options)
    }
    @PrivateEndpoint
    fun unpin(id: Long, vararg options: Pair<String, Any?>) = client.session.postObject<PinTweet>("/account/unpin_tweet.json") {
        form("id" to id, *options)
    }
}
