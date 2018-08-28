package jp.nephy.penicillin.core.emulation

enum class OfficialClient(val consumerKey: String, val consumerSecret: String) {
    TwitterForiPhone("IQKbtAYlXLripLGPWd0HUA", "GgDYlkSvaPxGxC4X8liwpUoqKwwr3lCADbz8A7ADU"),
    TwitterForAndroid("3nVuSoBZnx6U4vzUxf5w", "Bcs59EFbbsdF6Sl9Ng71smgStWEGwXXKSjYvPVt7qys"),
    TwitterForiPad("CjulERsDeqhhjSme66ECg", "IQWdVyqFxghAtURHGeGiWAsmCAGmdW3WmbEx6Hck");

    val appName: String
        get() = name.replace("For", " for ")
}
