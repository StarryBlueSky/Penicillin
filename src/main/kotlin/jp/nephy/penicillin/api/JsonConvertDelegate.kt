package jp.nephy.penicillin.api

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlin.reflect.KProperty

class JsonConvertDelegate<out T, R>(private val klass1: Class<T>, private val klass2: Class<R>, private val jsonObj: JsonObject, private val key: String?=null) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val element = jsonObj[key ?: property.name]
        val arg: Any = when (klass2) {
            String::class.java -> element.asString
            Long::class.java -> element.asLong
            JsonElement::class.java -> element
            else -> throw IllegalArgumentException("Unsupported for ${klass2.simpleName}.")
        }
        return klass1.getConstructor(klass2).newInstance(arg)
    }
}
