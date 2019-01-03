package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.session.Session

interface ApiAction<R> {
    val request: ApiRequest
    val session: Session
        get() = request.session

    suspend fun await(): R
}
