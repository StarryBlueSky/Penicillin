package jp.nephy.penicillin.request.credential

abstract class AbsCredential(private val key: String) {
    override fun toString(): String {
        return key
    }
}
