package jp.nephy.penicillin.model.special

import okhttp3.Headers

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
