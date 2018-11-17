package jp.nephy.penicillin.core.emulation

interface OfficialClient {
    val appName: String

    enum class OAuth1a(val consumerKey: String, val consumerSecret: String): OfficialClient {
        TwitterForiPhone("IQKbtAYlXLripLGPWd0HUA", "GgDYlkSvaPxGxC4X8liwpUoqKwwr3lCADbz8A7ADU"), TwitterForAndroid("3nVuSoBZnx6U4vzUxf5w", "Bcs59EFbbsdF6Sl9Ng71smgStWEGwXXKSjYvPVt7qys"),
        TwitterForiPad("CjulERsDeqhhjSme66ECg", "IQWdVyqFxghAtURHGeGiWAsmCAGmdW3WmbEx6Hck");

        override val appName by lazy { name.replace("For", " for ") }
    }

    enum class OAuth2(val bearerToken: String): OfficialClient {
        Tweetdeck("AAAAAAAAAAAAAAAAAAAAAF7aAAAAAAAASCiRjWvh7R5wxaKkFp7MM%2BhYBqM%3DbQ0JPmjU9F6ZoMhDfI4uTNAaQuTDm2uO9x3WFVr2xBZ2nhjdP0");

        override val appName by lazy { name }
    }
}
