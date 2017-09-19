package jp.nephy.penicillin.exception

abstract class AbsTwitterErrorMessage(val code: Int, val text: String, val description: String): Exception() {
    override val message = "${if (text.endsWith(".")) {text} else {text + "."}} ${if (description.endsWith(".")) {description} else {description + "."}} Returned with error code $code."
}
