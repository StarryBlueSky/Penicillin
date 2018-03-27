package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.UndocumentedAPI
import jp.nephy.penicillin.model.TrendArea
import jp.nephy.penicillin.model.TrendPlace
import jp.nephy.penicillin.model.TrendPlus
import jp.nephy.penicillin.parameters.TrendExclude
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject


class Trend(private val client: Client) {
    @GET
    fun getAvailableAreas(vararg options: Pair<String, String?>): ResponseList<TrendArea> {
        return client.session.new()
                .url("/trends/available.json")
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getClosestAreas(lat: Float, long: Float, vararg options: Pair<String, String?>): ResponseList<TrendArea> {
        return client.session.new()
                .url("/trends/closest.json")
                .param("lat" to lat)
                .param("long" to long)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getTrends(id: Long, exclude: TrendExclude?=null, vararg options: Pair<String, String?>): ResponseList<TrendPlace> {
        return client.session.new()
                .url("/trends/place.json")
                .param("id" to id)
                .param("exclude" to when (exclude) {
                    TrendExclude.Hashtags -> "hashtags"
                    else -> null
                })
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET @UndocumentedAPI
    fun getTrendPlus(vararg options: Pair<String, String?>): ResponseObject<TrendPlus> {
        return client.session.new()
                .url("/trends/plus.json")
                .param("cards_platform" to "iPhone-13")
                .param("contributor_details" to "1")
                .param("ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo")
                .param("include_cards" to "1")
                .param("include_carousels" to "1")
                .param("include_entities" to "1")
                .param("include_ext_media_color" to "true")
                .param("include_media_features" to "true")
                .param("include_my_retweet" to "1")
                .param("include_profile_interstitial_type" to "true")
                .param("include_profile_location" to "true")
                .param("include_reply_count" to "1")
                .param("include_user_entities" to "true")
                .param("include_user_hashtag_entities" to "true")
                .param("include_user_mention_entities" to "true")
                .param("include_user_symbol_entities" to "true")
                .param("pc" to "true")
                .param("tweet_mode" to "extended")
                .params(*options)
                .get()
                .getResponseObject()
    }
}
