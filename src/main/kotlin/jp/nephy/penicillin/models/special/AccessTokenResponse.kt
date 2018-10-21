@file:Suppress("UNUSED")

package jp.nephy.penicillin.models.special

data class AccessTokenResponse(val accessToken: String, val accessTokenSecret: String, val userId: Long, val screenName: String)
