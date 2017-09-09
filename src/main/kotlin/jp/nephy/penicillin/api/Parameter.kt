package jp.nephy.penicillin.api

class Parameter {
    private val parameters = mutableMapOf<String,String?>()

    fun put(key: String, value: String?) {
        parameters.put(key, value)
    }

    fun toList(): List<Pair<String,String?>> {
        return parameters.toList()
    }
}
