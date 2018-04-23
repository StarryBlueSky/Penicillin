package jp.nephy.penicillin.util

class Source(val value: String) {
    private val tag = ATagParser(value)
    val url = tag.attributes.getOrDefault("href", "http://url.invalid")
    val name = tag.value
}
