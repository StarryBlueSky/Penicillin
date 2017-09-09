package jp.nephy.penicillin.api

class ATagParser(html: String) {
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
