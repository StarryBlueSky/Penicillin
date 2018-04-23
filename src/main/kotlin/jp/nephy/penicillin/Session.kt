package jp.nephy.penicillin

import jp.nephy.jsonkt.JsonModel
import jp.nephy.penicillin.model.Cursor
import jp.nephy.penicillin.request.*
import jp.nephy.penicillin.streaming.StreamHandler
import okhttp3.OkHttpClient
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit


class Session(val consumerKey: String?, val consumerSecret: String?, val accessToken: String?, val accessTokenSecret: String?, val isOfficialClient: Boolean, val bearerToken: String?, httpClientBuilder: OkHttpClient.Builder.() -> Unit, val maxRetries: Int) {
    init {
        if (consumerKey == null && consumerSecret == null && accessToken == null && accessTokenSecret == null && bearerToken == null) {
            throw PenicillinLocalizedException(LocalizedString.CredentialsAreAllNull)
        }
    }

    val httpClient = OkHttpClient.Builder().apply(httpClientBuilder).apply {
        readTimeout(60, TimeUnit.SECONDS)
    }.build()!!
    val pool = ScheduledThreadPoolExecutor(5)

    inline fun <reified T: JsonModel> getObject(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): ObjectAction<T> {
        return ObjectAction(T::class.java, PenicillinRequestBuilder(this, HTTPMethod.GET, url, authorizationType).apply(operation))
    }
    inline fun <reified T: Cursor> getCursorObject(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): CursorObjectAction<T> {
        return CursorObjectAction(T::class.java, PenicillinRequestBuilder(this, HTTPMethod.GET, url, authorizationType).apply(operation))
    }
    inline fun <reified T: JsonModel> getList(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): ListAction<T> {
        return ListAction(T::class.java, PenicillinRequestBuilder(this, HTTPMethod.GET, url, authorizationType).apply(operation))
    }
    inline fun getText(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): TextAction {
        return TextAction(PenicillinRequestBuilder(this, HTTPMethod.GET, url, authorizationType).apply(operation))
    }
    inline fun <T: StreamHandler> getStream(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): StreamAction<T> {
        return StreamAction(PenicillinRequestBuilder(this, HTTPMethod.GET, url, authorizationType).apply(operation))
    }

    inline fun <reified T: JsonModel> postObject(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): ObjectAction<T> {
        return ObjectAction(T::class.java, PenicillinRequestBuilder(this, HTTPMethod.POST, url, authorizationType).apply(operation))
    }
    inline fun <reified T: Cursor> postCursorObject(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): CursorObjectAction<T> {
        return CursorObjectAction(T::class.java, PenicillinRequestBuilder(this, HTTPMethod.POST, url, authorizationType).apply(operation))
    }
    inline fun <reified T: JsonModel> postList(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): ListAction<T> {
        return ListAction(T::class.java, PenicillinRequestBuilder(this, HTTPMethod.POST, url, authorizationType).apply(operation))
    }
    inline fun postText(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): TextAction {
        return TextAction(PenicillinRequestBuilder(this, HTTPMethod.POST, url, authorizationType).apply(operation))
    }
    inline fun <T: StreamHandler> postStream(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): StreamAction<T> {
        return StreamAction(PenicillinRequestBuilder(this, HTTPMethod.POST, url, authorizationType).apply(operation))
    }

    inline fun <reified T: JsonModel> deleteObject(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): ObjectAction<T> {
        return ObjectAction(T::class.java, PenicillinRequestBuilder(this, HTTPMethod.DELETE, url, authorizationType).apply(operation))
    }
    inline fun <reified T: Cursor> deleteCursorObject(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): CursorObjectAction<T> {
        return CursorObjectAction(T::class.java, PenicillinRequestBuilder(this, HTTPMethod.DELETE, url, authorizationType).apply(operation))
    }
    inline fun <reified T: JsonModel> deleteList(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): ListAction<T> {
        return ListAction(T::class.java, PenicillinRequestBuilder(this, HTTPMethod.DELETE, url, authorizationType).apply(operation))
    }
    inline fun deleteText(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): TextAction {
        return TextAction(PenicillinRequestBuilder(this, HTTPMethod.DELETE, url, authorizationType).apply(operation))
    }
    inline fun <T: StreamHandler> deleteStream(url: String, authorizationType: AuthorizationType = AuthorizationType.OAuth1a, operation: PenicillinRequestBuilder.() -> Unit): StreamAction<T> {
        return StreamAction(PenicillinRequestBuilder(this, HTTPMethod.DELETE, url, authorizationType).apply(operation))
    }
}
