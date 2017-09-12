package jp.nephy.penicillin.converter

import com.google.gson.JsonObject
import jp.nephy.penicillin.misc.Country
import jp.nephy.penicillin.misc.StatusID
import jp.nephy.penicillin.model.Contributor
import jp.nephy.penicillin.model.MediaEntity
import kotlin.reflect.KProperty

class NullableJsonConvertArrayDelegate<T>(private val klass: Class<T>, private val jsonObj: JsonObject, private val key: String?=null) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): ArrayList<T>? {
        val element = jsonObj[key ?: property.name]
        if (element == null || element.isJsonNull) {
            return null
        }

        val result = arrayListOf<T>()
        element.asJsonArray.forEach {
            val arg: Any = when (klass) {
                Contributor::class.java -> Contributor(it)
                Country::class.java -> Country(it.asString)
                Float::class.java -> it.asFloat
                MediaEntity::class.java -> MediaEntity(it)
                String::class.java -> it.asString
                StatusID::class.java -> StatusID(it.asLong)
                Int::class.java -> it.asInt
                else -> throw IllegalArgumentException("Unsupported for ${klass.simpleName}.")
            }
            @Suppress("UNCHECKED_CAST")
            result.add(arg as T)
        }

        return result
    }
}