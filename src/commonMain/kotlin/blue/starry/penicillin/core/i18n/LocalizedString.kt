/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

package blue.starry.penicillin.core.i18n

/**
 * Represents localization strings. Supports Japanese and English.
 */
public class LocalizedString(
    /**
     * English string template.
     */
    public val en: String,

    /**
     * Japanese string template, or null.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    public val ja: String? = null
) {
    @Suppress("KDocMissingDocumentation")
    public companion object {
        public val PrivateEndpointRequiresOfficialClientEmulation: LocalizedString = LocalizedString(
            "Accessing private Twitter API requires official client's emulation.",
            "非公開 API へのアクセスには公式クライアントのエミュレーションが必要です。"
        )

        public val CursorIsZero: LocalizedString = LocalizedString(
            "cursor value is 0.",
            "cursor は 0 です。"
        )

        public val ApiRequestFailed: LocalizedString = LocalizedString(
            "Failed to request API: {}",
            "API のリクエストに失敗しました: {}"
        )

        public val ApiReturnedNon200StatusCode: LocalizedString = LocalizedString(
            "API returned non-200 status code: {} {}",
            "API が 2xx ではないステータスコードを返却しました: {} {}"
        )

        public val JsonParsingFailed: LocalizedString = LocalizedString(
            "Failed to parse JSON: {}",
            "JSON をパースする際に例外が発生しました: {}"
        )

        public val JsonModelCastFailed: LocalizedString = LocalizedString(
            "Failed to cast JSON to model class: {}",
            "JSON をモデルクラスにキャストする際に例外が発生しました: {}"
        )

        public val UnknownApiError: LocalizedString = LocalizedString(
            "Unknown API error occurred. ({}: {})\n{}",
            "不明な API エラーが発生しました。 ({}: {})\n{}"
        )

        public val UnknownApiErrorWithStatusCode: LocalizedString = LocalizedString(
            "Unknown API error occurred. (HTTP {})\n{}",
            "不明な API エラーが発生しました。 (HTTP {})\n{}"
        )
    }

    /**
     * Returns formatted string with passed arguments.
     */
    public operator fun invoke(vararg args: Any?): String {
        val text = when (defaultLocale) {
            Locale.Japanese -> ja
            else -> en
        } ?: en

        return args.fold(text) { s1, s2 -> s1.replaceFirst("{}", s2.toString()) }
    }

    override fun toString(): String {
        return invoke()
    }
}

/**
 * Indicates current OS locale.
 */
public enum class Locale {
    /**
     * Means current locale is English.
     */
    English,

    /**
     * Means current locale is Japanese.
     */
    Japanese
}

/**
 * Platform-based default [Locale].
 */
public expect val defaultLocale: Locale
