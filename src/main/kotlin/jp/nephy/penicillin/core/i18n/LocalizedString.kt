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

/**
 * Represents localization strings. Supports Japanese and English.
 */
class LocalizedString(
    /**
     * English string template.
     */
    val en: String,

    /**
     * Japanese string template, or null.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    val ja: String? = null
) {
    @Suppress("KDocMissingDocumentation", "PublicApiImplicitType")
    companion object {
        val PrivateEndpointRequiresOfficialClientEmulation = LocalizedString(
            "Accessing private Twitter API requires official client's emulation.",
            "非公開 API へのアクセスには公式クライアントのエミュレーションが必要です。"
        )

        val CursorIsZero = LocalizedString(
            "cursor value is 0.",
            "cursor は 0 です。"
        )

        val ApiRequestFailedLog = LocalizedString(
            "Failed to request API. (%s, %d/%d)",
            "API のリクエストに失敗しました。 (%s, %d/%d)"
        )

        val ApiRequestFailed = LocalizedString(
            "Failed to request API: %s",
            "API のリクエストに失敗しました: %s"
        )

        val ApiReturnedNon200StatusCode = LocalizedString(
            "API returned non-200 status code: %s %s",
            "API が 2xx ではないステータスコードを返却しました: %s %s"
        )

        val JsonParsingFailed = LocalizedString(
            "Failed to parse JSON: %s",
            "JSON をパースする際に例外が発生しました: %s"
        )

        val JsonModelCastFailed = LocalizedString(
            "Failed to cast JSON to model class: %s",
            "JSON をモデルクラスにキャストする際に例外が発生しました: %s"
        )

        val UnknownApiError = LocalizedString(
            "Unknown API error occurred. (%d: %s)\n%s",
            "不明な API エラーが発生しました。 (%d: %s)\n%s"
        )

        val UnknownApiErrorWithStatusCode = LocalizedString(
            "Unknown API error occurred. (HTTP %d)\n%s",
            "不明な API エラーが発生しました。 (HTTP %d)\n%s"
        )

        val ExceptionInAsyncBlock = LocalizedString(
            "Exception in async block.",
            "非同期実行中にエラーが発生しました。"
        )

        val SessionAlreadyClosed = LocalizedString(
            "The session is already closed.",
            "Session は既に閉じられています。"
        )
    }

    /**
     * Returns formatted string with passed arguments.
     */
    operator fun invoke(vararg args: Any?): String {
        val locale = Locale.getDefault()
        val text = when (locale) {
            Locale.JAPANESE, Locale.JAPAN -> ja
            else -> en
        } ?: en

        return text.format(locale, *args)
    }

    override fun toString(): String {
        return invoke()
    }
}
