package jp.nephy.penicillin.core.emulation

import io.ktor.http.Headers
import jp.nephy.penicillin.core.Session
import jp.nephy.penicillin.core.auth.OAuthUtil
import java.security.SecureRandom

class Twitter4iPhone(override val session: Session): Emulation() {
    override val headers = Headers.build {
        append("User-Agent", "Twitter-iPhone/7.21.2 iOS/11.0.2 (Apple;iPhone10,1;;;;;1;2017)")
        append("X-Twitter-Client", "Twitter-iPhone")
        append("X-Twitter-Client-DeviceID", deviceUUID)
        append("X-Twitter-Active-User", "yes")
        append("kdt", "")
        append("X-Twitter-Client-Version", "7.21.2")
        append("X-Twitter-Client-Limit-Ad-Tracking", "1")
        append("X-Twitter-Polling", "false")
        append("X-B3-TraceId", b3TraceId())
        append("Accept-Language", "ja")
        append("Timezone", "Asia/Tokyo")
        append("X-Twitter-Client-Language", "ja")
        append("X-Client-UUID", clientUUID)
        append("X-Twitter-API-Version", "5")
    }
    
    companion object {
        private val deviceUUID = OAuthUtil.randomUUID()
        private val clientUUID = OAuthUtil.randomUUID()

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
