package jp.nephy.penicillin.converter

import com.github.salomonbrys.kotson.obj
import com.google.gson.JsonElement

internal inline fun <reified R, reified T> JsonElement.byConverter(key: String?=null) = JsonConvertDelegate(T::class.java, R::class.java, this.obj, key)
internal inline fun <reified T> JsonElement.byModel(key: String?=null) = this.byConverter<JsonElement, T>(key)
internal inline fun <reified T> JsonElement.byList(key: String?=null, noinline converter: ((JsonElement) -> Any)={ it }) = JsonConvertArrayDelegate(T::class.java, this.obj, key, converter)
