package jp.nephy.penicillin.core.emulation

import io.ktor.http.Headers
import jp.nephy.penicillin.core.Session
import jp.nephy.penicillin.core.auth.OAuthUtil
import java.security.SecureRandom

class Twitter4iPhone(override val session: Session): Emulation() {
    override val headers = Headers.build {
        append("Accept-Language", "ja")
        append("Timezone", "Asia/Tokyo")
        append("User-Agent", "Twitter-iPhone/7.30 iOS/11.3.1 (Apple;iPhone10,1;;;;;1;2017)")
        append("X-B3-TraceId", b3TraceId())
        append("X-Client-UUID", clientUUID)
        append("X-Twitter-Active-User", "yes")
        append("X-Twitter-API-Version", "5")
        append("X-Twitter-Client", "Twitter-iPhone")
        append("X-Twitter-Client-DeviceID", "00000000-0000-0000-0000-000000000000")
        append("X-Twitter-Client-Language", "ja")
        append("X-Twitter-Client-Limit-Ad-Tracking", "1")
        append("X-Twitter-Client-Version", "7.30")
        append("X-Twitter-Polling", "true")
        append("X-Twitter-UTCOffset", "+0900")
    }

    companion object {
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
