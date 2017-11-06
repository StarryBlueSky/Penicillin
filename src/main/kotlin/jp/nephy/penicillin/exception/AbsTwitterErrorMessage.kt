package jp.nephy.penicillin.exception

abstract class AbsTwitterErrorMessage(val code: Int, val text: String, val description: String?): Exception() {
    override val message = StringBuilder().apply {
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
    }.toString()
}
