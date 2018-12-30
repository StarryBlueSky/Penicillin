package jp.nephy.penicillin.core.i18n

import java.util.*

enum class LocalizedString(private val ja: String, private val en: String) {
    CredentialsAreAllNull(
        "ConsumerKey, ConsumerSecret, AccessToken, AccessTokenSecret, BearerToken のすべてが null です。", "ConsumerKey, ConsumerSecret, AccessToken, AccessTokenSecret and BearerToken are all null."
    ),
    PrivateEndpointRequiresOfficialClientEmulation(
        "非公開 API へのアクセスには公式クライアントのエミュレーションが必要です。", "Access to private apis requires official client's emulation."
    ),
    CursorIsZero(
        "cursor は 0 です。", "cursor value is 0."
    ),
    ApiRequestFailedLog(
        "API のリクエストに失敗しました。 (%s, %d/%d)", "Failed to request API. (%s, %d/%d)"
    ),
    ApiRequestFailed(
        "API のリクエストに失敗しました: %s", "Failed to request API: %s"
    ),
    ApiReturnedNon200StatusCode(
        "API が 2xx ではないステータスコードを返却しました: %s %s", "API returned non-200 status code: %s %s"
    ),
    JsonParsingFailed(
        "JSON をパースする際に例外が発生しました: %s", "Failed to parse JSON: %s"
    ),
    JsonModelCastFailed(
        "JSON をモデルクラスにキャストする際に例外が発生しました: %s\n%s", "Failed to cast JSON to model class: %s\n%s"
    ),
    InvalidJsonReturned(
        "不正な JSON が返却されました。\n%s", "Invalid JSON returned.\n%s"
    ),
    UnknownApiError(
        "不明な API エラーが発生しました。 (%d: %s)\n%s", "Unknown API error occurred. (%d: %s)\n%s"
    ),
    UnknownApiErrorWithStatusCode(
        "不明な API エラーが発生しました。 (HTTP %d)\n%s", "Unknown API error occurred. (HTTP %d)\n%s"
    ),
    ExceptionInAsyncBlock(
        "非同期実行中にエラーが発生しました。", "Exception in async block."
    ),
    SessionAlreadyClosed(
        "Session は既に閉じられています。", "Session is already closed."
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
