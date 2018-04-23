package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoint.parameter.TrendExclude
import jp.nephy.penicillin.model.TrendArea
import jp.nephy.penicillin.model.TrendPlace
import jp.nephy.penicillin.model.TrendPlus


class Trend(override val client: PenicillinClient): Endpoint {
    fun availableAreas(vararg options: Pair<String, Any?>)= client.session.getList<TrendArea>("/trends/available.json") {
        query(*options)
    }

    fun closestAreas(lat: Float, long: Float, vararg options: Pair<String, Any?>)= client.session.getList<TrendArea>("/trends/closest.json") {
        query("lat" to lat, "long" to long, *options)
    }

    fun listPlaceTrends(id: Long, exclude: TrendExclude? = null, vararg options: Pair<String, Any?>)= client.session.getList<TrendPlace>("/trends/place.json") {
        query("id" to id, "exclude" to exclude?.value, *options)
    }

    @PrivateEndpoint
    fun trendPlus(vararg options: Pair<String, Any?>)= client.session.getObject<TrendPlus>("/trends/plus.json") {
        query("cards_platform" to "iPhone-13", "contributor_details" to "1", "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo", "include_cards" to "1", "include_carousels" to "1", "include_entities" to "1", "include_ext_media_color" to "true", "include_media_features" to "true", "include_my_retweet" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_reply_count" to "1", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "pc" to "true", "tweet_mode" to "extended", *options)
    }
}
