@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoints.parameters.TrendExclude
import jp.nephy.penicillin.models.TrendArea
import jp.nephy.penicillin.models.TrendPlace
import jp.nephy.penicillin.models.TrendPlus


class Trend(override val client: PenicillinClient): Endpoint {
    fun availableAreas(vararg options: Pair<String, Any?>) = client.session.get("/1.1/trends/available.json") {
        parameter(*options)
    }.jsonArray<TrendArea>()

    fun closestAreas(lat: Float, long: Float, vararg options: Pair<String, Any?>) = client.session.get("/1.1/trends/closest.json") {
        parameter("lat" to lat, "long" to long, *options)
    }.jsonArray<TrendArea>()

    fun listPlaceTrends(id: Long, exclude: TrendExclude? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/trends/place.json") {
        parameter("id" to id, "exclude" to exclude?.value, *options)
    }.jsonArray<TrendPlace>()

    @PrivateEndpoint
    fun trendPlus(vararg options: Pair<String, Any?>) = client.session.get("/1.1/trends/plus.json") {
        parameter("cards_platform" to "iPhone-13", "contributor_details" to "1", "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo", "include_cards" to "1", "include_carousels" to "1", "include_entities" to "1", "include_ext_media_color" to "true", "include_media_features" to "true", "include_my_retweet" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_reply_count" to "1", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "pc" to "true", "tweet_mode" to "extended", *options)
    }.jsonObject<TrendPlus>()
}
