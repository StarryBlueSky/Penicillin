package jp.nephy.penicillin.core.emulation

import jp.nephy.penicillin.core.auth.OAuthUtil
import java.security.SecureRandom

class Twitter4iPhone private constructor() {
    companion object {
        private val deviceUUID = OAuthUtil.randomUUID()
        private val clientUUID = OAuthUtil.randomUUID()

        val headers = arrayOf(
                "User-Agent" to "Twitter-iPhone/7.21.2 iOS/11.0.2 (Apple;iPhone10,1;;;;;1;2017)",
                "X-Twitter-Client" to "Twitter-iPhone",
                //"Geolocation" to "",
                "X-Twitter-Client-DeviceID" to deviceUUID,
                "X-Twitter-Active-User" to "yes",
                "kdt" to "",
                "X-Twitter-Client-Version" to "7.21.2",
                "X-Twitter-Client-Limit-Ad-Tracking" to "1",
                "X-Twitter-Polling" to "false",
                "X-B3-TraceId" to "",
                "Accept-Language" to "ja",
                "Timezone" to "Asia/Tokyo",
                "X-Twitter-Client-Language" to "ja",
                "X-Client-UUID" to clientUUID,
                "X-Twitter-API-Version" to "5"
        )

        fun b3TraceId(): String {
            val seed = SecureRandom()
            return buildString {
                repeat(16) {
                    append(Integer.toHexString(seed.nextInt(16)))
                }
            }
        }
    }
}
