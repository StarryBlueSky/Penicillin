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

@file:Suppress("UNUSED")

package tests

import blue.starry.jsonkt.JsonArray
import blue.starry.jsonkt.JsonElement
import blue.starry.jsonkt.JsonNull
import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.JsonPrimitive
import blue.starry.penicillin.core.i18n.LocalizedString
import blue.starry.penicillin.models.PenicillinModel
import com.google.common.base.CaseFormat
import kotlinx.serialization.json.*
import mu.KotlinLogging
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.KVisibility
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure

/**
 * A tool to tests.validate properties declared in model classes.
 * tests.ModelClassChecker is capable of suggesting property return types.
 * 
 * @param skipWarningsToDetailedType If true, model checking does not emit warnings about differences between detailed type (model class) and abstract type (current json type). For example, if true, `inReplyToStatusId` (Long?) is declared in model class and suggested type from json is JsonElement?, then tests.ModelClassChecker does not warn.
 * @param excludingPropertyNames A list of property names which you'd like to skip property checking. For example, extension properties cannot be resolved in tests.ModelClassChecker. In this situation, tests.ModelClassChecker warns that extension properties are not declared in model class.
 */
class ModelClassChecker(private val skipWarningsToDetailedType: Boolean = true, private val excludingPropertyNames: List<String> = emptyList()) {
    private val logger = KotlinLogging.logger("Penicillin.tests.ModelClassChecker")
    
    private val notDeclaredInModelClass = LocalizedString(
        "\"%s\" (%s) is not declared in model class: %s.\n%s",
        "\"%s\" (%s) はモデルクラス: %s で宣言されていません。\n%s"
    )
    private val differentTypeDeclaredInModelClass = LocalizedString(
        "\"%s\" (%s) has return type  which is different from suggested type: %s.\n%s",
        "\"%s\" (%s) は推定される返り値の型: %s と一致していません。\n%s"
    )

    fun <M: PenicillinModel> validate(models: Collection<M>) {
        if (models.isEmpty()) {
            throw IllegalArgumentException("models is empty.")
        }
        
        val modelClass = models.first()::class
        val modelProperties = modelClass.memberProperties.filter {
            it.name != PenicillinModel::client.name && it.name != PenicillinModel::json.name && it.visibility == KVisibility.PUBLIC
        }.associateBy {
            it.name
        }

        val elementGroup = models.flatMap { it.json.entries }.groupBy { it.key }.mapValues { it.value.map { entry -> entry.value } }
        for ((key, elements) in elementGroup) {
            val lowerCamelKey = key.snakeToLowerCamelCase()
            if (lowerCamelKey in excludingPropertyNames) {
                continue
            }
            
            val property = modelProperties[lowerCamelKey]
            val type = elements.presumeType()
            val name = if (key != lowerCamelKey) {
                "$key ($lowerCamelKey)"
            } else {
                key
            }
            
            if (property == null) {
                logger.warn { notDeclaredInModelClass(name, type.simpleSyntax, modelClass.simpleName, type.delegateSyntax(lowerCamelKey, key)) }
            } else if (property.returnType != type) {
                if (skipWarningsToDetailedType && property.returnType.isDetailThan(type)) {
                    // Skip
                } else {
                    logger.warn { differentTypeDeclaredInModelClass(name, property.returnType.simpleSyntax, type.simpleSyntax, type.delegateSyntax(lowerCamelKey, key)) }
                }
            }
        }
    }

    private fun String.snakeToLowerCamelCase(): String {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this)
    }
    
    private fun List<JsonElement>.presumeType(): KType {
        require(!isEmpty()) { "List is empty." }
        
        val classes = map { element ->
            when (element) {
                is JsonNull -> {
                    null
                }
                is JsonPrimitive -> {
                    when {
                        element.isString -> String::class
                        element.booleanOrNull != null -> Boolean::class
                        element.intOrNull != null -> Int::class
                        element.longOrNull != null -> Long::class
                        element.floatOrNull != null -> Float::class
                        element.doubleOrNull != null -> Double::class
                        else -> throw UnsupportedOperationException("Unsupported type: $element")
                    }
                }
                is JsonObject -> {
                    JsonObject::class
                }
                is JsonArray -> {
                    JsonArray::class
                }
                else -> {
                    throw UnsupportedOperationException("Unsupported type: $element")
                }
            }
        }

        return when {
            classes.all { it == null } -> {
                JsonElement::class.nullableType
            }
            classes.sameElements() -> {
                classes.firstNotNull()!!.type
            }
            classes.sameElementsOrNull() -> {
                classes.firstNotNull()!!.nullableType
            }
            classes.all { it == null || it.isSubclassOf(Number::class) } -> {
                val nulls = classes.count { it == null }
                val ints = classes.count { it == Int::class }
                val longs = classes.count { it == Long::class }
                val floats = classes.count { it == Float::class }
                val doubles = classes.count { it == Double::class }

                when (size) {
                    ints + longs -> Long::class.type
                    nulls + ints + longs -> Long::class.nullableType
                    floats + doubles -> Double::class.type
                    nulls + floats + doubles -> Double::class.nullableType
                    ints + longs + floats + doubles -> Double::class.type
                    else -> Double::class.nullableType
                }
            }
            else -> {
                throw IllegalStateException("Unknown state: $classes")
            }
        }
    }
    
    private fun <T> List<T>.firstNotNull(): T {
        return first { it != null }
    }
    
    private fun <T> List<T>.sameElements(): Boolean {
        val first = firstNotNull()
        return all { it == first }
    }
    
    private fun <T> List<T>.sameElementsOrNull(): Boolean {
        val first = firstNotNull()
        return all { it == first || it == null }
    }
    
    private val KType.simpleSyntax: String
        get() = buildString { 
            append(jvmErasure.simpleName ?: "Nothing")
            
            if (arguments.isNotEmpty()) {
                append("<${arguments.joinToString(", ") { it.type?.jvmErasure?.simpleName ?: "*" }}>")
            }
            
            if (isMarkedNullable) {
                append("?")
            }
        }
    
    private fun KType.isDetailThan(jsonType: KType): Boolean {
        return jsonType.isSupertypeOrSubtypeOf(JsonElement::class.type) && !isSupertypeOf(JsonElement::class.type)
    }
    
    private fun KType.delegateSyntax(name: String, key: String): String {
        return if (name == key) {
            "val $name by ${delegatePropertySyntax()}"
        } else {
            "val $name by ${delegatePropertySyntax(key)}"
        }
    }
    
    private fun KType.delegatePropertySyntax(key: String? = null): String {
        return buildString {
            when (this@delegatePropertySyntax) {
                Boolean::class.type -> "boolean"
                Boolean::class.nullableType -> "nullableBoolean"
                Int::class.type -> "int"
                Int::class.nullableType -> "nullableInt"
                Long::class.type -> "long"
                Long::class.nullableType -> "nullableLong"
                Float::class.type -> "float"
                Float::class.nullableType -> "nullableFloat"
                Double::class.type -> "double"
                Double::class.nullableType -> "nullableDouble"
                String::class.type -> "string"
                String::class.nullableType -> "nullableString"
                JsonElement::class.type -> "jsonElement"
                JsonElement::class.nullableType -> "nullableJsonElement"
                JsonObject::class.type -> "jsonObject"
                JsonObject::class.nullableType -> "nullableJsonObject"
                JsonArray::class.type -> "jsonArray"
                JsonArray::class.nullableType -> "nullableJsonArray"
                else -> "NOT_SUPPORTED"
            }.also { 
                append(it)
            }
            
            if (key != null) {
                append("(\"$key\")")
            }
        }
    }
    
    private fun KType.isSupertypeOrSubtypeOf(other: KType): Boolean {
        return isSupertypeOf(other) || isSubtypeOf(other)
    }
    
    private val KClass<*>.type: KType
        get() = createType()
    
    private val KClass<*>.nullableType: KType
        get() = createType(nullable = true)
}

fun <M: PenicillinModel> ModelClassChecker.validate(model: M) {
    validate(listOf(model))
}

fun <M: PenicillinModel> Collection<M>.validate() {
    ModelClassChecker().validate(this)
}

fun <M: PenicillinModel> M.validate() {
    ModelClassChecker().validate(this)
}
