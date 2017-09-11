package jp.nephy.penicillin.api

import java.text.SimpleDateFormat

class CreatedAt(private val createdAt: String) {
    private val pattern = "EEE, dd MMM yyyy HH:mm:ss ZZZZZ"

    fun toDate() = SimpleDateFormat(pattern).parse(createdAt)

    override fun toString() = createdAt
}
