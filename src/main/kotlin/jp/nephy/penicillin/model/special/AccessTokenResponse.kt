package jp.nephy.penicillin.model.special

data class AccessTokenResponse(val accessToken: String, val accessTokenSecret: String, val userId: Long, val screenName: String)
