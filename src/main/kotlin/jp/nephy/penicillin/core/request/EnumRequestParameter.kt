package jp.nephy.penicillin.core.request

/**
 * Represents an enum class can be serialized with "value" property.
 */
interface EnumRequestParameter {
    /**
     * The string which provides to endpoint request.
     */
    val value: String?
}
