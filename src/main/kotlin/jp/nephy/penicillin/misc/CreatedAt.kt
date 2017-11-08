package jp.nephy.penicillin.misc

import java.text.SimpleDateFormat

class CreatedAt(private val createdAt: String) {
    private val pattern = "EEE, dd MMM yyyy HH:mm:ss ZZZZZ"

    @Suppress("UNUSED")
    fun toDate() = SimpleDateFormat(pattern).parse(createdAt)

    override fun toString() = createdAt
}
