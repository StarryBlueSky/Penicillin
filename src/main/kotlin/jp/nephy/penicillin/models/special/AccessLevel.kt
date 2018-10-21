@file:Suppress("UNUSED")

package jp.nephy.penicillin.models.special

enum class AccessLevel {
    Read, ReadWrite, ReadWriteDM, Undefined;

    companion object {
        fun getLevel(value: String?): AccessLevel {
            return when (value?.toLowerCase()) {
                "read" -> Read
                "read-write" -> ReadWrite
                "read-write-directmessages" -> ReadWriteDM
                else -> Undefined
            }
        }
    }
}
