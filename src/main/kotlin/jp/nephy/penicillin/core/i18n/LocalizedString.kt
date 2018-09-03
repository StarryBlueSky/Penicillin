package jp.nephy.penicillin.core.i18n

import java.util.*

enum class LocalizedString(private val ja: String, private val en: String) {
    CredentialsAreAllNull(
            "ConsumerKey, ConsumerSecret, AccessToken, AccessTokenSecret, BearerTokenのすべてがnullです.",
            "ConsumerKey, ConsumerSecret, AccessToken, AccessTokenSecret and BearerToken are all null."
    ),
    PrivateEndpointRequiresOfficialClientEmulation(
            "非公開APIへのアクセスには公式クライアントのエミュレーションが必要です.",
            "Access to private apis requires official client's emulation."
    ),
    CursorIsZero(
            "カーソル(cursor)値は0です.",
            "cursor value is 0."
    ),
    ApiRequestFailedLog(
            "APIのリクエストに失敗しました. (%s, %d/%d)",
            "Failed to request API. (%s, %d/%d)"
    ),
    ApiRequestFailed(
            "APIのリクエストに失敗しました: %s",
            "Failed to request API: %s"
    ),
    StreamingApiRequestFailed(
            "Streaming APIのリクエストに失敗しました: %s %s",
            "Failed to request streaming API: %s %s"
    ),
    JsonParsingFailed(
            "JSONをパースする際に例外が発生しました: %s",
            "Failed to parse JSON: %s"
    ),
    JsonModelCastFailed(
            "JSONをモデルクラスにキャストする際に例外が発生しました: %s\n%s",
            "Failed to cast JSON to model class: %s\n%s"
    ),
    InvalidJsonReturned(
            "不正なJSONが返却されました.\n%s",
            "Invalid JSON returned.\n%s"
    ),
    UnknownApiError(
            "不明なAPIエラーが発生しました. (%d: %s)\n%s",
            "Unknown API error occurred. (%d: %s)\n%s"
    ),
    UnknownApiErrorWithStatusCode(
            "不明なAPIエラーが発生しました. (HTTP %d)\n%s",
            "Unknown API error occurred. (HTTP %d)\n%s"
    ),
    ExceptionInAsyncBlock(
            "非同期実行中にエラーが発生しました.",
            "Exception in async block."
    );

    fun format(vararg args: Any?): String {
        val locale = Locale.getDefault()
        val text = when (locale) {
            Locale.JAPANESE, Locale.JAPAN -> ja
            else -> en
        }
        return text.format(locale, *args)
    }

    override fun toString(): String {
        return format()
    }
}
