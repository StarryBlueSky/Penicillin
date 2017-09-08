package jp.nephy.penicillin

import jp.nephy.penicillin.api.Client
import jp.nephy.penicillin.request.credential.AccessToken
import jp.nephy.penicillin.request.credential.AccessTokenSecret
import jp.nephy.penicillin.request.credential.ConsumerKey
import jp.nephy.penicillin.request.credential.ConsumerSecret
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class API {
    companion object {
        fun authenticate(ck: ConsumerKey, cs : ConsumerSecret, at: AccessToken, ats: AccessTokenSecret, uuid: String?=null, deviceId: String?=null): Client {
            return Client(OAuthRequestHandler(ck, cs, at, ats, uuid, deviceId))
        }
    }
}
