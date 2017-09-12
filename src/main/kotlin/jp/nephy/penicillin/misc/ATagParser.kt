package jp.nephy.penicillin.misc

class ATagParser(html: String) {
    private val tagPattern = "^<a (.+?)>(.+?)</a>$".toRegex()
    private val attributePattern = "^(.+?)=\"(.+?)\"$".toRegex()
    private val matches = tagPattern.matchEntire(html)

    fun getAttributes(): Map<String,String> {
        return mutableMapOf<String,String>().apply {
            if (matches != null && matches.groupValues.size == 3) {
                matches.groupValues[1].split(" ").forEach {
                    val (name, value) = attributePattern.matchEntire(it)!!.destructured
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
