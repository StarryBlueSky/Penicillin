package jp.nephy.penicillin.model.special

data class RequestTokenResponse(val requestToken: String, val requestTokenSecret: String, val callbackConfirmed: Boolean)
