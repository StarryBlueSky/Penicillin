package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient

interface Endpoint {
    val client: PenicillinClient
}
