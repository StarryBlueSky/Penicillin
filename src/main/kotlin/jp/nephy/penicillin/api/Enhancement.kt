package jp.nephy.penicillin.api

import com.github.salomonbrys.kotson.obj
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.model.*
import jp.nephy.penicillin.api.model.List
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.net.URL
import kotlin.Error

internal fun String.GET(oauth: OAuthRequestHandler) = OAuthRequest(oauth, HTTPMethod.GET, this@GET)

internal fun String.POST(oauth: OAuthRequestHandler) = OAuthRequest(oauth, HTTPMethod.POST, this@POST)

internal fun String.DELETE(oauth: OAuthRequestHandler) = OAuthRequest(oauth, HTTPMethod.DELETE, this@DELETE)

internal val JsonElement.byStatus:    JsonConvertDelegate<Status,   JsonElement> get() = JsonConvertDelegate(Status::class.java,   JsonElement::class.java, this.obj)
internal val JsonElement.byUser:      JsonConvertDelegate<User,     JsonElement> get() = JsonConvertDelegate(User::class.java,     JsonElement::class.java, this.obj)
internal val JsonElement.byStatusID:  JsonConvertDelegate<StatusID, Long>        get() = JsonConvertDelegate(StatusID::class.java, Long::class.java,        this.obj)
internal val JsonElement.bySource:    JsonConvertDelegate<Source,   String>      get() = JsonConvertDelegate(Source::class.java,   String::class.java,      this.obj)
internal val JsonElement.byURL:       JsonConvertDelegate<URL,      String>      get() = JsonConvertDelegate(URL::class.java,      String::class.java,      this.obj)
internal val JsonElement.byStatusEntity: JsonConvertDelegate<StatusEntity, JsonElement> get() = JsonConvertDelegate(StatusEntity::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byPlaceType: JsonConvertDelegate<PlaceType, JsonElement> get() = JsonConvertDelegate(PlaceType::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byUserEntity: JsonConvertDelegate<UserEntity, JsonElement> get() = JsonConvertDelegate(UserEntity::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byUserProfileEntity: JsonConvertDelegate<UserProfileEntity, JsonElement> get() = JsonConvertDelegate(UserProfileEntity::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byRelationship: JsonConvertDelegate<Relationship, JsonElement> get() = JsonConvertDelegate(Relationship::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byRelationshipSource: JsonConvertDelegate<RelationshipSource, JsonElement> get() = JsonConvertDelegate(RelationshipSource::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byRelationshipTarget: JsonConvertDelegate<RelationshipTarget, JsonElement> get() = JsonConvertDelegate(RelationshipTarget::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byPhotoSize: JsonConvertDelegate<PhotoSize, JsonElement> get() = JsonConvertDelegate(PhotoSize::class.java, JsonElement::class.java, this.obj)
internal val JsonElement.byLanguage:    JsonConvertDelegate<Language,   String> get() = JsonConvertDelegate(Language::class.java,   String::class.java, this.obj)

internal val JsonElement.byLongArray: JsonConvertArrayDelegate<Long> get() = JsonConvertArrayDelegate(Long::class.java, this.obj)
internal val JsonElement.byListArray: JsonConvertArrayDelegate<List> get() = JsonConvertArrayDelegate(List::class.java, this.obj)
internal val JsonElement.byUserArray: JsonConvertArrayDelegate<User> get() = JsonConvertArrayDelegate(User::class.java, this.obj)
internal val JsonElement.byStatusIDArray: JsonConvertArrayDelegate<StatusID> get() = JsonConvertArrayDelegate(StatusID::class.java, this.obj)
internal val JsonElement.byStringArray: JsonConvertArrayDelegate<String> get() = JsonConvertArrayDelegate(String::class.java, this.obj)
internal val JsonElement.byIntArray: JsonConvertArrayDelegate<Int> get() = JsonConvertArrayDelegate(Int::class.java, this.obj)
internal val JsonElement.byHashtagEntityArray: JsonConvertArrayDelegate<HashtagEntity> get() = JsonConvertArrayDelegate(HashtagEntity::class.java, this.obj)
internal val JsonElement.byUserMentionEntityArray: JsonConvertArrayDelegate<UserMentionEntity> get() = JsonConvertArrayDelegate(UserMentionEntity::class.java, this.obj)
internal val JsonElement.byURLEntityArray: JsonConvertArrayDelegate<URLEntity> get() = JsonConvertArrayDelegate(URLEntity::class.java, this.obj)
internal val JsonElement.byLocationArray: JsonConvertArrayDelegate<Location> get() = JsonConvertArrayDelegate(Location::class.java, this.obj)
internal val JsonElement.byTrendArray: JsonConvertArrayDelegate<Trend> get() = JsonConvertArrayDelegate(Trend::class.java, this.obj)
internal val JsonElement.bySymbolEntityArray: JsonConvertArrayDelegate<SymbolEntity> get() = JsonConvertArrayDelegate(SymbolEntity::class.java, this.obj)
internal val JsonElement.byErrorArray: JsonConvertArrayDelegate<Error> get() = JsonConvertArrayDelegate(Error::class.java, this.obj)
internal val JsonElement.byStatusArray: JsonConvertArrayDelegate<Status> get() = JsonConvertArrayDelegate(Status::class.java, this.obj)

internal fun JsonElement.byStatus   (key: String?=null): JsonConvertDelegate<Status,   JsonElement> = JsonConvertDelegate(Status::class.java,   JsonElement::class.java, this.obj, key)
internal fun JsonElement.byUser     (key: String?=null): JsonConvertDelegate<User,     JsonElement> = JsonConvertDelegate(User::class.java,     JsonElement::class.java, this.obj, key)
internal fun JsonElement.byStatusID (key: String?=null): JsonConvertDelegate<StatusID, Long>        = JsonConvertDelegate(StatusID::class.java, Long::class.java,        this.obj, key)
internal fun JsonElement.bySource   (key: String?=null): JsonConvertDelegate<Source,   String>      = JsonConvertDelegate(Source::class.java,   String::class.java,      this.obj, key)
internal fun JsonElement.byURL      (key: String?=null): JsonConvertDelegate<URL,      String>      = JsonConvertDelegate(URL::class.java,      String::class.java,      this.obj, key)
internal fun JsonElement.byStatusEntity(key: String?=null): JsonConvertDelegate<StatusEntity, JsonElement> = JsonConvertDelegate(StatusEntity::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.byPlaceType(key: String?=null): JsonConvertDelegate<PlaceType, JsonElement> = JsonConvertDelegate(PlaceType::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.byUserEntity(key: String?=null): JsonConvertDelegate<UserEntity, JsonElement> = JsonConvertDelegate(UserEntity::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.byUserProfileEntity(key: String?=null): JsonConvertDelegate<UserProfileEntity, JsonElement> = JsonConvertDelegate(UserProfileEntity::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.bySearchMetadata(key: String?=null): JsonConvertDelegate<SearchMetadata, JsonElement> = JsonConvertDelegate(SearchMetadata::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.byTimeZone(key: String?=null): JsonConvertDelegate<TimeZone, JsonElement> = JsonConvertDelegate(TimeZone::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.bySettingMetadata(key: String?=null): JsonConvertDelegate<SettingMetadata, JsonElement> = JsonConvertDelegate(SettingMetadata::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.bySleepTime(key: String?=null): JsonConvertDelegate<SleepTime, JsonElement> = JsonConvertDelegate(SleepTime::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.byPhoto(key: String?=null): JsonConvertDelegate<Photo, JsonElement> = JsonConvertDelegate(Photo::class.java, JsonElement::class.java, this.obj, key)
internal fun JsonElement.byCreatedAt   (key: String?=null): JsonConvertDelegate<CreatedAt,   String> = JsonConvertDelegate(CreatedAt::class.java,   String::class.java, this.obj, key)

internal fun JsonElement.byLongArray(key: String?=null): JsonConvertArrayDelegate<Long> = JsonConvertArrayDelegate(Long::class.java, this.obj, key)
internal fun JsonElement.byListArray(key: String?=null): JsonConvertArrayDelegate<List> = JsonConvertArrayDelegate(List::class.java, this.obj, key)
internal fun JsonElement.byUserArray(key: String?=null): JsonConvertArrayDelegate<User> = JsonConvertArrayDelegate(User::class.java, this.obj, key)
internal fun JsonElement.byStatusIDArray(key: String?=null): JsonConvertArrayDelegate<StatusID> = JsonConvertArrayDelegate(StatusID::class.java, this.obj, key)
internal fun JsonElement.byStringArray(key: String?=null): JsonConvertArrayDelegate<String> = JsonConvertArrayDelegate(String::class.java, this.obj, key)
internal fun JsonElement.byIntArray(key: String?=null): JsonConvertArrayDelegate<Int> = JsonConvertArrayDelegate(Int::class.java, this.obj, key)
internal fun JsonElement.byHashtagEntityArray(key: String?=null): JsonConvertArrayDelegate<HashtagEntity> = JsonConvertArrayDelegate(HashtagEntity::class.java, this.obj, key)
internal fun JsonElement.byUserMentionEntityArray(key: String?=null): JsonConvertArrayDelegate<UserMentionEntity> = JsonConvertArrayDelegate(UserMentionEntity::class.java, this.obj, key)
internal fun JsonElement.byURLEntityArray(key: String?=null): JsonConvertArrayDelegate<URLEntity> = JsonConvertArrayDelegate(URLEntity::class.java, this.obj, key)
internal fun JsonElement.byLocationArray(key: String?=null): JsonConvertArrayDelegate<Location> = JsonConvertArrayDelegate(Location::class.java, this.obj, key)
internal fun JsonElement.byTrendArray(key: String?=null): JsonConvertArrayDelegate<Trend> = JsonConvertArrayDelegate(Trend::class.java, this.obj, key)
internal fun JsonElement.bySymbolEntityArray(key: String?=null): JsonConvertArrayDelegate<SymbolEntity> = JsonConvertArrayDelegate(SymbolEntity::class.java, this.obj, key)

internal val JsonElement.byNullableStatus   : NullableJsonConvertDelegate<Status,   JsonElement> get() = NullableJsonConvertDelegate(Status::class.java,   JsonElement::class.java, this.obj)
internal val JsonElement.byNullableUser     : NullableJsonConvertDelegate<User,     JsonElement> get() = NullableJsonConvertDelegate(User::class.java,     JsonElement::class.java, this.obj)
internal val JsonElement.byNullableStatusID : NullableJsonConvertDelegate<StatusID, Long>        get() = NullableJsonConvertDelegate(StatusID::class.java, Long::class.java,        this.obj)
internal val JsonElement.byNullableSource   : NullableJsonConvertDelegate<Source,   String>      get() = NullableJsonConvertDelegate(Source::class.java,   String::class.java,      this.obj)
internal val JsonElement.byNullableURL      : NullableJsonConvertDelegate<URL,      String>      get() = NullableJsonConvertDelegate(URL::class.java,      String::class.java,      this.obj)

internal fun JsonElement.byNullableStatus   (key: String?=null): NullableJsonConvertDelegate<Status,   JsonElement> = NullableJsonConvertDelegate(Status::class.java,   JsonElement::class.java, this.obj, key)
internal fun JsonElement.byNullableUser     (key: String?=null): NullableJsonConvertDelegate<User,     JsonElement> = NullableJsonConvertDelegate(User::class.java,     JsonElement::class.java, this.obj, key)
internal fun JsonElement.byNullableStatusID (key: String?=null): NullableJsonConvertDelegate<StatusID, Long>        = NullableJsonConvertDelegate(StatusID::class.java, Long::class.java,        this.obj, key)
internal fun JsonElement.byNullableSource   (key: String?=null): NullableJsonConvertDelegate<Source,   String>      = NullableJsonConvertDelegate(Source::class.java,   String::class.java,      this.obj, key)
internal fun JsonElement.byNullableURL      (key: String?=null): NullableJsonConvertDelegate<URL,      String>      = NullableJsonConvertDelegate(URL::class.java,      String::class.java,      this.obj, key)
