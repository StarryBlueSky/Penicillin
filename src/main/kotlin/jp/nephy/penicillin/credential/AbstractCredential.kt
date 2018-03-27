package jp.nephy.penicillin.credential

abstract class AbstractCredential(private val key: String) {
    override fun toString() = key
}
