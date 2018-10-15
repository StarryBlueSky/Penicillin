package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class UserTypeahead(override val json: JsonObject): PenicillinModel {
    val canMediaTag by boolean("can_media_tag")
    val connectingUserCount by int("connecting_user_count")
    val connectingUserIds by longList("connecting_user_ids")
    val id by long
    val idStr by string("id_str")
    val inline by boolean
    val isBlocked by boolean("is_blocked")
    val isDmAble by boolean("is_dm_able")
    val isProtected by boolean("is_protected")
    val location by nullableString
    val name by string
    val profileImageUrl by nullableString("profile_image_url")
    val profileImageUrlHttps by nullableString("profile_image_url_https")
    val roundedGraphWeight by int("rounded_graph_weight")
    val roundedScore by int("rounded_score")
    val screenName by string("screen_name")
    val socialContext by model<SocialContext>(key = "social_context")
    val socialProof by int("social_proof")
    val socialProofsOrdered by intList("social_proofs_ordered")
    val tokens by modelList<SearchToken>()
    val verified by boolean
}
