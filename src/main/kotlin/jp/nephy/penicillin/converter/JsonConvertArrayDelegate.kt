package jp.nephy.penicillin.converter

import com.google.gson.JsonObject
import jp.nephy.penicillin.exception.EmptyJsonElementException
import jp.nephy.penicillin.misc.Country
import jp.nephy.penicillin.misc.StatusID
import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.model.List
import kotlin.reflect.KProperty

class JsonConvertArrayDelegate<T>(private val klass: Class<T>, private val jsonObj: JsonObject, private val key: String?=null) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): ArrayList<T> {
        val result = arrayListOf<T>()
        val element = jsonObj[key ?: property.name]
        if (element == null || element.isJsonNull) {
            throw EmptyJsonElementException(key ?: property.name)
        }

        element.asJsonArray.forEach {
            val arg: Any = when (klass) {
                Long::class.java -> it.asLong
                List::class.java -> List(it)
                User::class.java -> User(it)
                StatusID::class.java -> StatusID(it.asLong)
                String::class.java -> it.asString
                Int::class.java -> it.asInt
                HashtagEntity::class.java -> HashtagEntity(it)
                UserMentionEntity::class.java -> UserMentionEntity(it)
                Location::class.java -> Location(it)
                Trend::class.java -> Trend(it)
                URLEntity::class.java -> URLEntity(it)
                SymbolEntity::class.java -> SymbolEntity(it)
                Error::class.java -> Error(it)
                Status::class.java -> Status(it)
                Float::class.java -> it.asFloat
                Country::class.java -> Country(it.asString)
                Place::class.java -> Place(it)
                FaceCoordinate::class.java -> FaceCoordinate(it)
                VideoFile::class.java -> VideoFile(it)
                else -> throw IllegalArgumentException("Unsupported for ${klass.simpleName}.")
            }
            @Suppress("UNCHECKED_CAST")
            result.add(arg as T)
        }

        return result
    }
}