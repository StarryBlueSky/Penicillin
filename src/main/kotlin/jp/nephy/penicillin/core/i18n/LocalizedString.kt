/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package jp.nephy.penicillin.core.i18n

import java.util.*

data class LocalizedString(private val ja: String, private val en: String) {
    companion object {
        val CredentialsAreAllNull = LocalizedString(
        "ConsumerKey, ConsumerSecret, AccessToken, AccessTokenSecret, BearerToken のすべてが null です。", "ConsumerKey, ConsumerSecret, AccessToken, AccessTokenSecret and BearerToken are all null."
        )
        val PrivateEndpointRequiresOfficialClientEmulation = LocalizedString(
        "非公開 API へのアクセスには公式クライアントのエミュレーションが必要です。", "Access to private apis requires official client's emulation."
        )
        val CursorIsZero = LocalizedString(
        "cursor は 0 です。", "cursor value is 0."
        )
        val ApiRequestFailedLog = LocalizedString(
        "API のリクエストに失敗しました。 (%s, %d/%d)", "Failed to request API. (%s, %d/%d)"
        )
        val ApiRequestFailed = LocalizedString(
        "API のリクエストに失敗しました: %s", "Failed to request API: %s"
        )
        val ApiReturnedNon200StatusCode = LocalizedString(
        "API が 2xx ではないステータスコードを返却しました: %s %s", "API returned non-200 status code: %s %s"
        )
        val JsonParsingFailed = LocalizedString(
        "JSON をパースする際に例外が発生しました: %s", "Failed to parse JSON: %s"
        )
        val JsonModelCastFailed = LocalizedString(
        "JSON をモデルクラスにキャストする際に例外が発生しました: %s\n%s", "Failed to cast JSON to model class: %s\n%s"
        )
        val UnknownApiError = LocalizedString(
        "不明な API エラーが発生しました。 (%d: %s)\n%s", "Unknown API error occurred. (%d: %s)\n%s"
        )
        val UnknownApiErrorWithStatusCode = LocalizedString(
        "不明な API エラーが発生しました。 (HTTP %d)\n%s", "Unknown API error occurred. (HTTP %d)\n%s"
        )
        val ExceptionInAsyncBlock = LocalizedString(
        "非同期実行中にエラーが発生しました。", "Exception in async block."
        )
        val SessionAlreadyClosed = LocalizedString(
        "Session は既に閉じられています。", "Session is already closed."
        )
    }

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
