package jp.nephy.penicillin.endpoint

import com.google.gson.Gson
import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.*
import jp.nephy.penicillin.auth.AuthorizationType
import jp.nephy.penicillin.misc.StatusID
import jp.nephy.penicillin.model.CursorIds
import jp.nephy.penicillin.model.Embed
import jp.nephy.penicillin.model.Search
import jp.nephy.penicillin.model.Status
import jp.nephy.penicillin.parameters.*
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNUSED")
class Status(private val client: Client) {
    @GET
    fun getStatus(id: StatusID, trimUser: Boolean?=null, includeMyRetweet: Boolean?=null, includeEntities: Boolean?=null, includeExtAltText: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Status> {
        return client.session.new()
                .url("/statuses/show.json")
                .param("id" to id)
                .param("trim_user" to trimUser)
                .param("include_my_retweet" to includeMyRetweet)
                .param("include_entities" to includeEntities)
                .param("include_ext_alt_text" to includeExtAltText)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getStatuses(id: Array<StatusID>, trimUser: Boolean?=null, map: Boolean?=null, includeEntities: Boolean?=null, includeExtAltText: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<Status> {
        return client.session.new()
                .url("/statuses/lookup.json")
                .param("id" to id.joinToString(","))
                .param("trim_user" to trimUser)
                .param("map" to map)
                .param("include_entities" to includeEntities)
                .param("include_ext_alt_text" to includeExtAltText)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET @Cursorable
    fun getRetweeterIds(id: StatusID, stringifyIds: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<CursorIds> {
        return client.session.new()
                .url("/statuses/retweeters/ids.json")
                .param("id" to id)
                .param("stringify_ids" to stringifyIds)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getRetweets(id: StatusID, count: Int?=null, trimUser: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<Status> {
        return client.session.new()
                .url("/statuses/retweets/$id.json")
                .param("count" to count)
                .param("trim_user" to trimUser)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getRetweetedMyTweets(count: Int?=null, sinceId: StatusID?=null, maxId: StatusID?=null, trimUser: Boolean?=null, includeEntities: Boolean?=null, includeUserEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<Status> {
        return client.session.new()
                .url("/statuses/retweets_of_me.json")
                .param("count" to count)
                .param("since_id" to sinceId)
                .param("max_id" to maxId)
                .param("trim_user" to trimUser)
                .param("include_entities" to includeEntities)
                .param("include_user_entities" to includeUserEntities)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getHomeTimeline(count: Int?=null, sinceId: StatusID?=null, maxId: StatusID?=null, trimUser: Boolean?=null, excludeReplies: Boolean?=null, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<Status> {
        return client.session.new()
                .url("/statuses/home_timeline.json")
                .param("count" to count)
                .param("since_id" to sinceId)
                .param("max_id" to maxId)
                .param("trim_user" to trimUser)
                .param("exclude_replies" to excludeReplies)
                .param("include_entities" to includeEntities)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getMentionTimeline(count: Int?=null, sinceId: StatusID?=null, maxId: StatusID?=null, trimUser: Boolean?=null, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<Status> {
        return client.session.new()
                .url("/statuses/mentions_timeline.json")
                .paramIfOfficial("cards_platform" to "iPhone-13")
                .paramIfOfficial("contributor_details" to "1")
                .paramIfOfficial("count" to "20")
                .paramIfOfficial("ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo")
                .paramIfOfficial("filters" to "")
                .paramIfOfficial("forceBuckets" to "")
                .paramIfOfficial("include_cards" to "1")
                .paramIfOfficial("include_carousels" to "1")
                .paramIfOfficial("include_entities" to "1")
                .paramIfOfficial("include_ext_media_color" to "true")
                .paramIfOfficial("include_media_features" to "true")
                .paramIfOfficial("include_my_retweet" to "1")
                .paramIfOfficial("include_profile_interstitial_type" to "true")
                .paramIfOfficial("include_profile_location" to "true")
                .paramIfOfficial("include_reply_count" to "1")
                .paramIfOfficial("include_user_entities" to "true")
                .paramIfOfficial("include_user_hashtag_entities" to "true")
                .paramIfOfficial("include_user_mention_entities" to "true")
                .paramIfOfficial("include_user_symbol_entities" to "true")
                .paramIfOfficial("tweet_mode" to "extended")
                .param("count" to count)
                .param("since_id" to sinceId)
                .param("max_id" to maxId)
                .param("trim_user" to trimUser)
                .param("include_entities" to includeEntities)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getUserTimeline(userId: Long?=null, screenName: String?=null, count: Int?=null, sinceId: StatusID?=null, maxId: StatusID?=null, trimUser: Boolean?=null, excludeReplies: Boolean?=null, includeRTs: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<Status> {
        return client.session.new()
                .url("/statuses/user_timeline.json")
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("count" to count)
                .param("since_id" to sinceId)
                .param("max_id" to maxId)
                .param("trim_user" to trimUser)
                .param("exclude_replies" to excludeReplies)
                .param("include_rts" to includeRTs)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getEmbedFormat(url: String, maxwidth: Int?=null, hideMedia: Boolean?=null, hideThread: Boolean?=null, omitScript: Boolean?=null, align: EmbedAlign?=null, related: Array<String>?=null, lang: String?=null, theme: String?=null, linkColor: String?=null, widgetType: EmbedWidgetType?=null, dnt: Boolean?=null,  vararg options: Pair<String, String?>): ResponseObject<Embed> {
        return client.session.new()
                .type(AuthorizationType.NONE)
                .url("https://publish.twitter.com/oembed")
                .param("url" to url)
                .param("maxwidth" to maxwidth)
                .param("hide_media" to hideMedia)
                .param("hide_thread" to hideThread)
                .param("omit_script" to omitScript)
                .param("align" to when (align) {
                    EmbedAlign.Center -> "center"
                    EmbedAlign.Left -> "left"
                    EmbedAlign.Right -> "right"
                    EmbedAlign.None -> "none"
                    else -> null
                })
                .param("related" to related?.joinToString(","))
                .param("lang" to lang)
                .param("theme" to theme)
                .param("link_color" to linkColor)
                .param("widget_type" to when (widgetType) {
                    EmbedWidgetType.Video -> "video"
                    else -> null
                })
                .param("dnt" to dnt)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST
    fun update(status: String, inReplyToStatusId: StatusID?=null, possiblySensitive: Boolean?=null, lat: Float?=null, long: Float?=null, placeId: String?=null, displayCoordinates: Boolean?=null, trimUser: Boolean?=null, mediaIds: Array<Long>?=null, enableDMCommands: Boolean?=null, failDMCommands: Boolean?=null, cardUri: String?=null, vararg options: Pair<String, String?>): ResponseObject<Status> {
        return client.session.new()
                .url("/statuses/update.json")
                .dataAsFormIfOfficial("auto_populate_reply_metadata" to "true")
                .dataAsFormIfOfficial("batch_mode" to "off")
                .dataAsFormIfOfficial("cards_platform" to "iPhone-13")
                .dataAsFormIfOfficial("contributor_details" to "1")
                .dataAsFormIfOfficial("ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo")
                .dataAsFormIfOfficial("include_cards" to "1")
                .dataAsFormIfOfficial("include_carousels" to "1")
                .dataAsFormIfOfficial("include_entities" to "1")
                .dataAsFormIfOfficial("include_ext_media_color" to "true")
                .dataAsFormIfOfficial("include_media_features" to "true")
                .dataAsFormIfOfficial("include_my_retweet" to "1")
                .dataAsFormIfOfficial("include_profile_interstitial_type" to "true")
                .dataAsFormIfOfficial("include_profile_location" to "true")
                .dataAsFormIfOfficial("include_reply_count" to "1")
                .dataAsFormIfOfficial("include_user_entities" to "true")
                .dataAsFormIfOfficial("include_user_hashtag_entities" to "true")
                .dataAsFormIfOfficial("include_user_mention_entities" to "true")
                .dataAsFormIfOfficial("include_user_symbol_entities" to "true")
                .dataAsFormIfOfficial("tweet_mode" to "extended")
                .dataAsFormIfOfficial("weighted_character_count" to "true")
                .dataAsForm("status" to status)
                .dataAsForm("card_uri" to cardUri)
                .dataAsForm("in_reply_to_status_id" to inReplyToStatusId)
                .dataAsForm("possibly_sensitive" to possiblySensitive)
                .dataAsForm("lat" to lat)
                .dataAsForm("long" to long)
                .dataAsForm("place_id" to placeId)
                .dataAsForm("display_coordinates" to displayCoordinates)
                .dataAsForm("trim_user" to trimUser)
                .dataAsForm("media_ids" to mediaIds?.joinToString(","))
                .dataAsForm("enable_dm_commands" to enableDMCommands)
                .dataAsForm("fail_dm_commands" to failDMCommands)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun destroy(id: StatusID, trimUser: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Status> {
        return client.session.new()
                .url("/statuses/destroy/$id.json")
                .dataAsForm("trim_user" to trimUser)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun retweet(id: StatusID, trimUser: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Status> {
        return client.session.new()
                .url("/statuses/retweet/$id.json")
                .dataAsForm("trim_user" to trimUser)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun unretweet(id: StatusID, trimUser: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Status> {
        return client.session.new()
                .url("/statuses/unretweet/$id.json")
                .dataAsForm("trim_user" to trimUser)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }


    @GET
    fun search(q: String, geocode: String?=null, lang: String?=null, locale: String?=null, resultType: SearchResultType?=null, count: Int?=null, until: Date?=null, sinceId: StatusID?=null, maxId: StatusID?=null, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Search> {
        return client.session.new()
                .url("/search/tweets.json")
                .param("q" to q)
                .param("geocode" to geocode)
                .param("lang" to lang)
                .param("locale" to locale)
                .param("result_type" to when (resultType) {
                    SearchResultType.Mixed -> "mixed"
                    SearchResultType.Recent -> "recent"
                    SearchResultType.Popular -> "popular"
                    else -> null
                })
                .param("count" to count)
                .param("until" to if (until != null) SimpleDateFormat("yyyy-MM-dd").format(until) else (null))
                .param("since_id" to sinceId)
                .param("max_id" to maxId)
                .param("include_entities" to includeEntities)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST @Recipe
    fun updateWithMediaFile(status: String, media: Array<Pair<File, MediaType>>, vararg options: Pair<String, String?>): ResponseObject<Status> {
        val mediaIds = media.map {
            client.media.uploadMediaFile(it.first, it.second, MediaCategory.TweetImage).result.mediaId
        }

        return update(status, mediaIds =  mediaIds.toTypedArray(), options = *options)
    }

    @POST @Recipe
    fun updateWithMedia(status: String, media: Array<Pair<ByteArray, MediaType>>, vararg options: Pair<String, String?>): ResponseObject<Status> {
        val mediaIds = media.map {
            client.media.uploadMedia(it.first, it.second, MediaCategory.TweetImage).result.mediaId
        }

        return update(status, mediaIds =  mediaIds.toTypedArray(), options = *options)
    }

    @POST @Recipe @UndocumentedAPI
    fun createPollTweet(status: String, choices: Array<String>, minutes: Int=1440, vararg options: Pair<String, String?>): ResponseObject<Status> {
        if (status.length > 140) {
            throw IllegalArgumentException("status must have less than 140 charactors.")
        }
        if (choices.size < 2 || choices.size > 5) {
            throw IllegalArgumentException("choices must have 2, 3 or 4 Strings.")
        }
        if (minutes < 0 || minutes > 10080) {
            throw IllegalArgumentException("minutes must be in range 1..10080.")
        }

        val card = client.card.create(
                cardData=Gson().toJson(linkedMapOf<String,Any>().apply {
                    choices.forEachIndexed { i, choice ->
                        put("twitter:string:choice${i + 1}_label", choice)
                    }
                    put("twitter:api:api:endpoint", "1")
                    put("twitter:card", "poll${choices.size}choice_text_only")
                    put("twitter:long:duration_minutes", minutes)
                })
        )

        return update(status, cardUri=card.result.cardUri, options=*options)
    }
}
