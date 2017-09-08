package jp.nephy.penicillin.api.model

import java.util.*

class StatusID(private val statusId: Long) {
    fun toLong(): Long {
        return statusId
    }

    override fun toString(): String {
        return statusId.toString()
    }

    fun toDate(): Date {
        return Date((statusId shr 22) + 1288834974657)
    }
}

class EpochTime(private val time: Long) {
    fun toDate(): Date {
        if (time < 10000000000) {
            return Date(time * 1000)
        }
        return Date(time)
    }

    override fun toString(): String {
        return toDate().toString()
    }
}

private class ATagParser(html: String) {
    private val TAG_PATTERN = "^<a (.+?)>(.+?)</a>$".toRegex()
    private val ATTRIBUTE_PATTERN = "^(.+?)=\"(.+?)\"$".toRegex()
    private val matches = TAG_PATTERN.matchEntire(html)

    fun getAttributes(): Map<String,String> {
        return mutableMapOf<String,String>().apply {
            if (matches != null && matches.groupValues.size == 3) {
                matches.groupValues[1].split(" ").forEach {
                    val (name, value) = ATTRIBUTE_PATTERN.matchEntire(it)!!.destructured
                    put(name, value)
                }
            }
        }
    }

    fun getValue(): String {
        return if (matches != null && matches.groupValues.size == 3) {
            matches.groupValues[2]
        } else {
            ""
        }
    }
}

class Source(private val source: String) {
    private val tag = ATagParser(source)
    val sourceUrl = tag.getAttributes()["href"]
    val sourceName = tag.getValue()
    val viaUrl = sourceUrl
    val viaName = sourceName

    override fun toString(): String {
        return source
    }
}
