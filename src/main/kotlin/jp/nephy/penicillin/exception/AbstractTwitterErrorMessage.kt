package jp.nephy.penicillin.exception

abstract class AbstractTwitterErrorMessage(val code: Int, val text: String, val description: String?): Exception() {
    override val message = buildString {
        append(text)
        if (! text.endsWith(".")) {
            append(".")
        }
        if (description != null) {
            append(description)
            if (! description.endsWith(".")) {
                append(".")
            }
        }
        append(" Returned with error code $code.")
    }
}
