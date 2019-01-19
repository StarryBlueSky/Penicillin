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

@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.models.Help

val PenicillinClient.help: jp.nephy.penicillin.endpoints.Help
    get() = Help(this)

class Help(override val client: PenicillinClient): Endpoint {
    fun configuration(vararg options: Pair<String, Any?>) = client.session.get("/1.1/help/configuration.json") {
        parameter(*options)
    }.jsonObject<Help.Configuration>()

    fun languages(vararg options: Pair<String, Any?>) = client.session.get("/1.1/help/languages.json") {
        parameter(*options)
    }.jsonArray<Help.Language>()

    fun privacy(vararg options: Pair<String, Any?>) = client.session.get("/1.1/help/privacy.json") {
        parameter(*options)
    }.jsonObject<Help.Privacy>()

    fun tos(vararg options: Pair<String, Any?>) = client.session.get("/1.1/help/tos.json") {
        parameter(*options)
    }.jsonObject<Help.Tos>()

    @PrivateEndpoint
    fun setting(includeZeroRate: Boolean? = null, settingsVersion: String? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/help/settings.json") {
        parameter("include_zero_rate" to includeZeroRate, "settings_version" to settingsVersion, *options)
    }.jsonObject<Help.Settings>()
}
