/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("Unused")

package tests

import blue.starry.jsonkt.*
import kotlinx.serialization.json.int
import kotlin.reflect.KClass

fun main() {

}

data class JsonModelOption(
    val topModelName: String? = null,
    val allowShort: Boolean = false,
    val allowChar: Boolean = false,
    val printComments: Boolean = true
) {
    companion object {
        val Default: JsonModelOption = JsonModelOption()
    }
}

class JsonModelUtils(private val option: JsonModelOption) {
    companion object {
        val Default: JsonModelUtils = JsonModelUtils(JsonModelOption.Default)
    }

    fun check(input: String) {
        check(input.toJsonElement())
    }

    fun check(input: JsonElement) {

    }

    fun createModelClass(input: JsonObject) {
        buildString {
            append("import blue.starry.jsonkt.*\n")
            append("import blue.starry.jsonkt.delegation.*\n\n")

            append("data class ${option.topModelName ?: "Model"}(override val json: JsonObject): JsonModel {")
            append("}")
        }
    }

    private fun calculateKClass(element: JsonElement): KClass<*>? {
        return when (element) {
            is JsonObject -> {
                val result = element.mapValues { (_, value) ->
                    calculateKClass(value)
                }

                TODO()
            }
            is JsonArray -> {
                val result = element.map { value ->
                    calculateKClass(value)
                }

                TODO()
            }
            is JsonPrimitive -> {
                var kClass: KClass<*>? = when {
                    element is JsonNull || element.isNull() -> null
                    element.isBoolean -> Boolean::class
                    element.isInt -> Int::class
                    element.isLong -> Long::class
                    element.isFloat -> Float::class
                    element.isDouble -> Double::class
                    element.isString -> String::class
                    else -> error("Unknown JsonPrimitive: $element")
                }

                if (option.allowShort && kClass == Int::class) {
                    val value = element.int
                    if (Short.MIN_VALUE <= value && value <= Short.MAX_VALUE) {
                        kClass = Short::class
                    }
                }
                if (option.allowChar && kClass == String::class) {
                    val value = element.string
                    if (value.length == 1) {
                        kClass = String::class
                    }
                }

                kClass
            }
            else -> {
                error("Unknown JsonElement: $element")
            }
        }
    }

    private fun calculatePropertySyntax(key: String, element: JsonElement, nullable: Boolean): String {
        return buildString {
            append("val ")

            val propertyName = key.toSafeKotlinLiteral().toLowerCamelCase()
            append(propertyName)

            append(" by ")

            TODO("delegation")

            if (option.printComments) {
                append("  // ")

                when (element) {
                    is JsonObject -> {
                        val value = element.toString()
                        when {
                            element.isEmpty() -> append("{}")
                            value.length > 50 -> append("{...}")
                            element.size == 1 -> append("{$value}")
                            else -> append("{$value, ...}")
                        }
                    }
                    is JsonArray -> {
                        val value = firstOrNull().toString()
                        when {
                            element.isEmpty() -> append("[]")
                            value.length > 50 -> append("[...]")
                            element.size == 1 -> append("[$value]")
                            else -> append("[$value, ...]")
                        }
                    }
                    else -> {
                        if (nullable) {
                            append("${toString()} or null")
                        } else {
                            append(toString())
                        }
                    }
                }
            }
        }
    }

    private fun String.toLowerCamelCase(): String {
        val part = split("_")
        return part.first().decapitalize() + part.drop(1).joinToString("") { it.capitalize() }
    }

    private fun String.toSafeKotlinLiteral(): String {
        return run {
            // Kotlin literal must start with alphabets or "_".
            replace("^\\d".toRegex()) { "_${it.value}" }
        }.run {
            // Non-alphabets characters are replace with "_".
            replace("\\W+".toRegex(), "_")
        }
    }
}

typealias JsonPair = Pair<String, JsonElement>

sealed class JsonProperty(private val pair: JsonPair) {

}

class JsonPrimitiveProperty(pair: JsonPair): JsonProperty(pair)
