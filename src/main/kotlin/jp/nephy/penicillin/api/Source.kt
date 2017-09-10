package jp.nephy.penicillin.api

class Source(private val source: String) {
    private val tag = ATagParser(source)
    val sourceUrl = tag.getAttributes()["href"]
    val sourceName = tag.getValue()
    val viaUrl = sourceUrl
    val viaName = sourceName

    override fun toString(): String = source
}
