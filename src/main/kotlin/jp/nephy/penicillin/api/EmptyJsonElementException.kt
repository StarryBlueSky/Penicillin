package jp.nephy.penicillin.api

class EmptyJsonElementException(key: String): Exception() {
    override var message: String = "Json was null or empty when accessing `$key`."
}