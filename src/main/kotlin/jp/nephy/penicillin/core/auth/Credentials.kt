package jp.nephy.penicillin.core.auth

import jp.nephy.penicillin.core.PenicillinLocalizedException
import jp.nephy.penicillin.core.emulation.OfficialClient
import jp.nephy.penicillin.core.i18n.LocalizedString

data class Credentials(val consumerKey: String?, val consumerSecret: String?, val accessToken: String?, val accessTokenSecret: String?, val bearerToken: String?, val knownDeviceToken: String?) {
    class Builder {
        private var ck: String? = null
        private var cs: String? = null
        private var kdt: String? = null
        fun application(client: OfficialClient, knownDeviceToken: String? = null) {
            ck = client.consumerKey
            cs = client.consumerSecret
            kdt = knownDeviceToken
        }

        fun application(consumerKey: String, consumerSecret: String, knownDeviceToken: String? = null) {
            ck = consumerKey
            cs = consumerSecret
            kdt = knownDeviceToken
        }

        private var at: String? = null
        private var ats: String? = null
        fun token(accessToken: String, accessTokenSecret: String) {
            at = accessToken
            ats = accessTokenSecret
        }

        private var bt: String? = null
        fun token(bearerToken: String) {
            bt = bearerToken
        }

        internal fun build(): Credentials {
            if (ck == null && cs == null && at == null && ats == null && bt == null) {
                throw PenicillinLocalizedException(LocalizedString.CredentialsAreAllNull)
            }

            return Credentials(ck, cs, at, ats, bt, kdt)
        }
    }
}
