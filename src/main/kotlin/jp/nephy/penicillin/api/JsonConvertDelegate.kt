package jp.nephy.penicillin.api

import com.google.gson.JsonObject
import kotlin.reflect.KProperty

class JsonConvertDelegate<out T, R>(private val klass1: Class<T>, private val klass2: Class<R>, private val jsonObj: JsonObject, private val key: String?=null) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val element = jsonObj[key ?: property.name]
        val arg: Any = when (klass2.simpleName) {
            "String" -> element.asString
            "long" -> element.asLong
            else -> element
        }
        return klass1.getConstructor(klass2).newInstance(arg)
    }
}
