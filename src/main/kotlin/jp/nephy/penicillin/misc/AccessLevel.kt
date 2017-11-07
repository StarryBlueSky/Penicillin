package jp.nephy.penicillin.misc

import okhttp3.Headers

enum class AccessLevel {
    Read, ReadWrite, ReadWriteDM, Undefined;

    companion object {
        fun getLevel(headers: Headers): AccessLevel {
            val levelName = if (headers["x-access-level"] != null) {
                headers["x-access-level"]!!
            } else {
                null
            }

            return when (levelName) {
                "read" -> Read
                "read-write" -> ReadWrite
                "read-write-directmessages" -> ReadWriteDM
                else -> Undefined
            }
        }
    }
}
