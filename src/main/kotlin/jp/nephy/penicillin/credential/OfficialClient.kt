package jp.nephy.penicillin.credential

enum class OfficialClient {
    Twitter_for_iPhone,
    Twitter_for_Android,
    Twitter_for_iPad,
    Twitter_for_Mac,
    Twitter_for_Windows_Phone;

    fun getCredentials(): Pair<ConsumerKey, ConsumerSecret> {
        return when (this) {
            Twitter_for_iPhone -> Pair("IQKbtAYlXLripLGPWd0HUA", "GgDYlkSvaPxGxC4X8liwpUoqKwwr3lCADbz8A7ADU")
            Twitter_for_Android -> Pair("3nVuSoBZnx6U4vzUxf5w", "Bcs59EFbbsdF6Sl9Ng71smgStWEGwXXKSjYvPVt7qys")
            Twitter_for_iPad -> Pair("CjulERsDeqhhjSme66ECg", "IQWdVyqFxghAtURHGeGiWAsmCAGmdW3WmbEx6Hck")
            Twitter_for_Mac -> Pair("3rJOl1ODzm9yZy63FACdg", "5jPoQ5kQvMJFDYRNE8bQ4rHuds4xJqhvgNJM4awaE8")
            Twitter_for_Windows_Phone -> Pair("yN3DUNVO0Me63IAQdhTfCA", "c768oTKdzAjIYCmpSNIdZbGaG0t6rOhSFQP0S5uC79g")
        }.run {
            Pair(ConsumerKey(this.first), ConsumerSecret(this.second))
        }
    }

    override fun toString() = this.name.replace("_", " ")
}
