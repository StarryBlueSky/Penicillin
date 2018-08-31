package jp.nephy.penicillin.core.emulation

import io.ktor.http.Headers
import jp.nephy.penicillin.core.Session

abstract class Emulation {
    abstract val session: Session
    open val headers: Headers = Headers.Empty
}
