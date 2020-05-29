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

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.int
import blue.starry.jsonkt.delegation.nullableModel
import blue.starry.jsonkt.delegation.string
import blue.starry.penicillin.core.session.ApiClient


data class UserProfileBanner(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val resolution1080x360 by nullableModel("1080x360") { Banner(it, client) }
    val resolution1500x500 by nullableModel("1500x500") { Banner(it, client) }
    val resolution300x100 by nullableModel("300x100") { Banner(it, client) }
    val resolution600x200 by nullableModel("600x200") { Banner(it, client) }
    val ipad by nullableModel { Banner(it, client) }
    val ipadRetina by nullableModel("ipad_retina") { Banner(it, client) }
    val mobile by nullableModel { Banner(it, client) }
    val mobileRetina by nullableModel("mobile_retina") { Banner(it, client) }
    val web by nullableModel { Banner(it, client) }
    val webRetina by nullableModel("web_retina") { Banner(it, client) }

    data class Banner(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val h by int
        val w by int
        val url by string
    }
}
