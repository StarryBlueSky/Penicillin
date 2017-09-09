package jp.nephy.penicillin.api

import com.github.salomonbrys.kotson.obj
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.model.TweetModel
import jp.nephy.penicillin.api.model.UserModel
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.net.URL

internal fun String.GET(oauth: OAuthRequestHandler): OAuthRequest {
    return OAuthRequest(oauth, HTTPMethod.GET, this@GET)
}

internal fun String.POST(oauth: OAuthRequestHandler): OAuthRequest {
    return OAuthRequest(oauth, HTTPMethod.POST, this@POST)
}

internal fun String.DELETE(oauth: OAuthRequestHandler): OAuthRequest {
    return OAuthRequest(oauth, HTTPMethod.DELETE, this@DELETE)
}

internal val JsonElement.byTweetModel: JsonConvertDelegate<TweetModel,JsonElement> get() = JsonConvertDelegate(TweetModel::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byUserModel : JsonConvertDelegate<UserModel, JsonElement> get() = JsonConvertDelegate(UserModel::class.java,  JsonElement::class.java, this.obj)
internal val JsonElement.byStatusID  : JsonConvertDelegate<StatusID,  Long>        get() = JsonConvertDelegate(StatusID::class.java,   Long::class.java,        this.obj)
internal val JsonElement.bySource    : JsonConvertDelegate<Source,    String>      get() = JsonConvertDelegate(Source::class.java,     String::class.java,      this.obj)
internal val JsonElement.byURL       : JsonConvertDelegate<URL,       String>      get() = JsonConvertDelegate(URL::class.java,        String::class.java,      this.obj)

internal fun JsonElement.byTweetModel(key: String?=null): JsonConvertDelegate<TweetModel,JsonElement> = JsonConvertDelegate(TweetModel::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.byUserModel (key: String?=null): JsonConvertDelegate<UserModel, JsonElement> = JsonConvertDelegate(UserModel::class.java,  JsonElement::class.java, this.obj, key)
internal fun JsonElement.byStatusID  (key: String?=null): JsonConvertDelegate<StatusID,  Long>        = JsonConvertDelegate(StatusID::class.java,   Long::class.java,        this.obj, key)
internal fun JsonElement.bySource    (key: String?=null): JsonConvertDelegate<Source,    String>      = JsonConvertDelegate(Source::class.java,     String::class.java,      this.obj, key)
internal fun JsonElement.byURL       (key: String?=null): JsonConvertDelegate<URL,       String>      = JsonConvertDelegate(URL::class.java,        String::class.java,      this.obj, key)

internal val JsonElement.byNullableTweetModel: NullableJsonConvertDelegate<TweetModel,JsonElement> get() = NullableJsonConvertDelegate(TweetModel::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byNullableUserModel : NullableJsonConvertDelegate<UserModel, JsonElement> get() = NullableJsonConvertDelegate(UserModel::class.java,  JsonElement::class.java, this.obj)
internal val JsonElement.byNullableStatusID  : NullableJsonConvertDelegate<StatusID,  Long>        get() = NullableJsonConvertDelegate(StatusID::class.java,   Long::class.java,        this.obj)
internal val JsonElement.byNullableSource    : NullableJsonConvertDelegate<Source,    String>      get() = NullableJsonConvertDelegate(Source::class.java,     String::class.java,      this.obj)
internal val JsonElement.byNullableURL       : NullableJsonConvertDelegate<URL,       String>      get() = NullableJsonConvertDelegate(URL::class.java,        String::class.java,      this.obj)

internal fun JsonElement.byNullableTweetModel(key: String?=null): NullableJsonConvertDelegate<TweetModel,JsonElement> = NullableJsonConvertDelegate(TweetModel::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.byNullableUserModel (key: String?=null): NullableJsonConvertDelegate<UserModel, JsonElement> = NullableJsonConvertDelegate(UserModel::class.java,  JsonElement::class.java, this.obj, key)
internal fun JsonElement.byNullableStatusID  (key: String?=null): NullableJsonConvertDelegate<StatusID,  Long>        = NullableJsonConvertDelegate(StatusID::class.java,   Long::class.java,        this.obj, key)
internal fun JsonElement.byNullableSource    (key: String?=null): NullableJsonConvertDelegate<Source,    String>      = NullableJsonConvertDelegate(Source::class.java,     String::class.java,      this.obj, key)
internal fun JsonElement.byNullableURL       (key: String?=null): NullableJsonConvertDelegate<URL,       String>      = NullableJsonConvertDelegate(URL::class.java,        String::class.java,      this.obj, key)
