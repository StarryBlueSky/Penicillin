package jp.nephy.penicillin.models.special

import io.ktor.http.Headers

enum class AccessLevel {
    Read, ReadWrite, ReadWriteDM, Undefined;

    companion object {
        fun getLevel(headers: Headers): AccessLevel {
            return when (headers["x-access-level"]) {
                "read" -> Read
                "read-write" -> ReadWrite
                "read-write-directmessages" -> ReadWriteDM
                else -> Undefined
            }
        }
    }
}
