@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.response.ApiResponse

val ApiResponse.accessLevel: AccessLevel?
    get() = AccessLevel.from(response.headers["x-access-level"])

enum class AccessLevel(private val identifier: String) {
    Read("read"), ReadWrite("read-write"), ReadWriteDM("read-write-directmessages");

    companion object {
        fun from(levelName: String?): AccessLevel? {
            return values().find { it.identifier.equals(levelName, true) }
        }
    }
}
