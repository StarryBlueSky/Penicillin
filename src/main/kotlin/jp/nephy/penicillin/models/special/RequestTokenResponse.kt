@file:Suppress("UNUSED")

package jp.nephy.penicillin.models.special

data class RequestTokenResponse(val requestToken: String, val requestTokenSecret: String, val callbackConfirmed: Boolean)
