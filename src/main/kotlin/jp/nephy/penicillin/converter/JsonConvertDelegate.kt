package jp.nephy.penicillin.converter

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import jp.nephy.penicillin.exception.EmptyJsonElementException
import kotlin.reflect.KProperty

class JsonConvertDelegate<out T, R>(private val klass1: Class<T>, private val klass2: Class<R>, private val jsonObj: JsonObject, private val key: String?) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val element = jsonObj[key ?: property.name]
        if (element == null || element.isJsonNull) {
            if (property.returnType.isMarkedNullable) {
                @Suppress("UNCHECKED_CAST")
                return null as T
            }

            throw EmptyJsonElementException(key ?: property.name)
        }

        return klass1.getConstructor(klass2).newInstance(element.run {
            @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
            when (klass2) {
                java.lang.String::class.java -> asString
                java.lang.Integer::class.java -> asInt
                java.lang.Long::class.java -> asLong
                java.lang.Float::class.java -> asFloat
                JsonElement::class.java -> this
                else -> throw UnsupportedOperationException(klass2.name)
            } as R
        })
    }
}
