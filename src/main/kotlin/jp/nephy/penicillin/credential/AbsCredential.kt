package jp.nephy.penicillin.credential

abstract class AbsCredential(private val key: String) {
    override fun toString() = key
}
