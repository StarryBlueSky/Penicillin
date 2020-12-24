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

@file:Suppress("UNUSED")

package blue.starry.penicillin.core.emulation

/**
 * Represents Twitter official clients.
 */
public interface OfficialClient {
    /**
     * Application name.
     */
    public val appName: String

    /**
     * Pre-defined official clients for OAuth 1.0a.
     */
    public enum class OAuth1a(
        override val appName: String,

        /**
         * Consumer key.
         */
        public val consumerKey: String,

        /**
         * Consumer secret.
         */
        public val consumerSecret: String
    ): OfficialClient {
        /**
         * Corresponding to "Twitter for iPhone".
         */
        TwitterForiPhone("Twitter for iPhone", "IQKbtAYlXLripLGPWd0HUA", "GgDYlkSvaPxGxC4X8liwpUoqKwwr3lCADbz8A7ADU"),

        /**
         * Corresponding to "Twitter for Android".
         */
        TwitterForAndroid("Twitter for Android", "3nVuSoBZnx6U4vzUxf5w", "Bcs59EFbbsdF6Sl9Ng71smgStWEGwXXKSjYvPVt7qys"),

        /**
         * Corresponding to "Twitter for iPad".
         */
        TwitterForiPad("Twitter for iPad", "CjulERsDeqhhjSme66ECg", "IQWdVyqFxghAtURHGeGiWAsmCAGmdW3WmbEx6Hck")
    }

    /**
     * Pre-defined official clients for OAuth 2.
     */
    public enum class OAuth2(
        override val appName: String,

        /**
         * Bearer token.
         */
        public val bearerToken: String
    ): OfficialClient {
        /**
         * Corresponding to "Tweetdeck".
         */
        Tweetdeck("Tweetdeck", "AAAAAAAAAAAAAAAAAAAAAF7aAAAAAAAASCiRjWvh7R5wxaKkFp7MM%2BhYBqM%3DbQ0JPmjU9F6ZoMhDfI4uTNAaQuTDm2uO9x3WFVr2xBZ2nhjdP0")
    }
}
