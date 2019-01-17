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

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.int
import jp.nephy.jsonkt.delegation.nullableModel
import jp.nephy.jsonkt.delegation.string

data class UserProfileBanner(override val json: JsonObject): PenicillinModel {
    val resolution1080x360 by nullableModel<Banner>(key = "1080x360")
    val resolution1500x500 by nullableModel<Banner>(key = "1500x500")
    val resolution300x100 by nullableModel<Banner>(key = "300x100")
    val resolution600x200 by nullableModel<Banner>(key = "600x200")
    val ipad by nullableModel<Banner>()
    val ipadRetina by nullableModel<Banner>(key = "ipad_retina")
    val mobile by nullableModel<Banner>()
    val mobileRetina by nullableModel<Banner>(key = "mobile_retina")
    val web by nullableModel<Banner>()
    val webRetina by nullableModel<Banner>(key = "web_retina")

    data class Banner(override val json: JsonObject): PenicillinModel {
        val h by int
        val w by int
        val url by string
    }
}
