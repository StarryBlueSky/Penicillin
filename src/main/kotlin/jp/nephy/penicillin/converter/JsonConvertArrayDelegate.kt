package jp.nephy.penicillin.converter

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlin.reflect.KProperty

class JsonConvertArrayDelegate<out T>(private val klass: Class<T>, private val jsonObj: JsonObject, private val key: String?=null, private val converter: ((JsonElement) -> Any)={it}) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): List<T> {
        val element = jsonObj[key ?: property.name]
        if (element == null || element.isJsonNull || !element.isJsonArray) {
            return listOf()
        }

        return element.asJsonArray.map {
            it.run {
                @Suppress("UNCHECKED_CAST")
                when (klass) {
                    java.lang.String::class.java -> asString
                    java.lang.Integer::class.java -> asInt
                    java.lang.Long::class.java -> asLong
                    java.lang.Float::class.java -> asFloat
                    else -> {
                        try {
                            klass.declaredConstructors[0].newInstance(converter(this))
                        } catch (e: IllegalArgumentException) {
                            throw UnsupportedOperationException(klass.name)
                        }
                    }
                } as T
            }
        }.toList()
    }
}
