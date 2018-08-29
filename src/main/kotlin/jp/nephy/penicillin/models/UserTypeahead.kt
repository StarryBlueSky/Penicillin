package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*


data class UserTypeahead(override val json: JsonObject): PenicillinModel {
    val canMediaTag by json.byBool("can_media_tag")
    val connectingUserCount by json.byInt("connecting_user_count")
    val connectingUserIds by json.byLongList("connecting_user_ids")
    val id by json.byLong
    val idStr by json.byString("id_str")
    val inline by json.byBool
    val isBlocked by json.byBool("is_blocked")
    val isDmAble by json.byBool("is_dm_able")
    val isProtected by json.byBool("is_protected")
    val location by json.byNullableString
    val name by json.byString
    val profileImageUrl by json.byNullableString("profile_image_url")
    val profileImageUrlHttps by json.byNullableString("profile_image_url_https")
    val roundedGraphWeight by json.byInt("rounded_graph_weight")
    val roundedScore by json.byInt("rounded_score")
    val screenName by json.byString("screen_name")
    val socialContext by json.byModel<SocialContext>(key = "social_context")
    val socialProof by json.byInt("social_proof")
    val socialProofsOrdered by json.byIntList("social_proofs_ordered")
    val tokens by json.byModelList<SearchToken>()
    val verified by json.byBool
}
