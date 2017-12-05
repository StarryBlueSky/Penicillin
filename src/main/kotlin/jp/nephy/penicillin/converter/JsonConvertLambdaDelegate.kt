package jp.nephy.penicillin.converter

import com.github.salomonbrys.kotson.contains
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlin.reflect.KProperty

class JsonConvertLambdaDelegate<out T>(private val jsonObj: JsonObject, private val key: String?, private val lambda: ((JsonElement).() -> T)) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (! jsonObj.contains(key ?: property.name)) {
            if (property.returnType.isMarkedNullable) {
                @Suppress("UNCHECKED_CAST")
                return null as T
            }

            throw IllegalStateException(key ?: property.name)
        }

        return lambda(jsonObj[key ?: property.name])
    }
}
