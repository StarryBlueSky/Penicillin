package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoints.parameter.SearchResultType
import jp.nephy.penicillin.models.Search
import jp.nephy.penicillin.models.SearchTypeahead
import jp.nephy.penicillin.models.SearchUniversal
import java.text.SimpleDateFormat
import java.util.*

class Search(override val client: PenicillinClient): Endpoint {
    fun search(q: String, geocode: String? = null, lang: String? = null, locale: String? = null, resultType: SearchResultType? = null, count: Int? = null, until: Date? = null, sinceId: Long? = null, maxId: Long? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getObject<Search>("/search/tweets.json") {
        query("q" to q, "geocode" to geocode, "lang" to lang, "locale" to locale, "result_type" to resultType?.value, "count" to count, "until" to if (until != null) {
            SimpleDateFormat("yyyy-MM-dd").format(until)
        } else {
            null
        }, "since_id" to sinceId, "max_id" to maxId, "include_entities" to includeEntities, *options)
    }

    @PrivateEndpoint
    fun typeahead(q: String, vararg options: Pair<String, Any?>)= client.session.getObject<SearchTypeahead>("/search/typeahead.json") {
        query("cards_platform" to "iPhone-13", "contributor_details" to "1", "count" to "1200", "ext" to "altText,info360,mediaColor,mediaRestrictions,mediaStats,stickerInfo", "include_cards" to "1", "include_carousels" to "1", "include_entities" to "1", "include_ext_media_color" to "true", "include_media_features" to "true", "include_my_retweet" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_reply_count" to "1", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "media_tagging_in_prefetch" to "true", "prefetch" to "true", "result_type" to "all", "src" to "search_box", "tweet_mode" to "extended", "users_cache_age" to "146522", "q" to q, *options)
    }

    @PrivateEndpoint
    fun searchUniversal(q: String, modules: String? = null, resultType: SearchResultType? = null, vararg options: Pair<String, Any?>) = client.session.getObject<SearchUniversal>("/search/universal.json") {
        query("q" to q, "modules" to modules, "result_type" to resultType?.value, *options)
    }
}
