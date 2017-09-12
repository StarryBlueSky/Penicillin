package jp.nephy.penicillin.exception

class EmptyJsonElementException(key: String): Exception() {
    override var message: String = "Json was null or empty when accessing `$key`."
}