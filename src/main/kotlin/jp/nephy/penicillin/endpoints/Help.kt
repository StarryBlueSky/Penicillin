package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.*


class Help(override val client: PenicillinClient): Endpoint {
    fun configuration(vararg options: Pair<String, Any?>)= client.session.getObject<Configuration>("/help/configuration.json") {
        query(*options)
    }

    fun languages(vararg options: Pair<String, Any?>)= client.session.getList<Language>("/help/languages.json") {
        query(*options)
    }

    fun privacy(vararg options: Pair<String, Any?>)= client.session.getObject<Privacy>("/help/privacy.json") {
        query(*options)
    }

    fun tos(vararg options: Pair<String, Any?>)= client.session.getObject<Tos>("/help/tos.json") {
        query(*options)
    }

    @PrivateEndpoint
    fun setting(includeZeroRate: Boolean? = null, settingsVersion: String? = null, vararg options: Pair<String, Any?>)= client.session.getObject<Empty>("/help/settings.json") {
        query("include_zero_rate" to includeZeroRate, "settings_version" to settingsVersion, *options)
    }
}
