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

@file:Suppress("UNUSED", "PublicApiImplicitType", "Deprecation", "KotlinDeprecation")

package jp.nephy.penicillin.extensions.endpoints

import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.endpoints.Account
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.account.updateProfileBackgroundImage
import jp.nephy.penicillin.endpoints.media
import jp.nephy.penicillin.endpoints.media.MediaComponent
import jp.nephy.penicillin.endpoints.media.uploadMedia
import jp.nephy.penicillin.extensions.DelegatedAction
import jp.nephy.penicillin.extensions.await
import jp.nephy.penicillin.models.User

/**
 * Update profile background image.
 *
 * @param media Uploading media.
 * @param tile Optional. Whether or not to tile the background image. If set to true , t or 1 the background image will be displayed tiled. The image will not be tiled otherwise.
 * @param includeEntities Optional. The entities node will not be included when set to false.
 * @param skipStatus Optional. When set to either true, t or 1 statuses will not be included in the returned user object.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Account] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 */
fun Account.updateProfileBackgroundImage(
    media: MediaComponent,
    tile: Boolean? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
) = DelegatedAction {
    val result = client.media.uploadMedia(media).await()
    updateProfileBackgroundImage(result.mediaId, tile, includeEntities, skipStatus, *options)
}
