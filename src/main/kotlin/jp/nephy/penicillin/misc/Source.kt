package jp.nephy.penicillin.misc

class Source(private val source: String) {
    private val tag = ATagParser(source)
    val url = tag.getAttributes().getOrDefault("href", "http://localhost")
    val name = tag.getValue()

    override fun toString(): String = source
}
