@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.models.Status

private val tagPattern = "^<a (.+?)>(.+?)</a>$".toRegex()
private val attributePattern = "^(.+?)=\"(.+?)\"$".toRegex()

val Status.via: Via
    get() {
        val matches = tagPattern.matchEntire(source)

        val tagAttributes = matches?.groupValues?.getOrNull(1)?.split(" ")?.map {
            val (k, v) = attributePattern.matchEntire(it)!!.destructured
            k to v
        }?.toMap()
        val tagValue = matches?.groupValues?.getOrNull(2)
        val href = tagAttributes?.get("href")

        if (tagAttributes == null || tagValue == null || href == null) {
            throw IllegalArgumentException("Invalid source html passed.")
        }

        return Via(href, tagValue, tagAttributes)
    }

data class Via(val url: String, val name: String, val attributes: Map<String, String>)
