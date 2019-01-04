package jp.nephy.penicillin.core.emulation

import io.ktor.http.Headers
import jp.nephy.penicillin.core.session.Session

interface Emulation {
    val session: Session
    val headers: Headers
        get() = Headers.Empty
}
