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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package blue.starry.penicillin.endpoints.accountactivity

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.formBody
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.AccountActivity
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.Webhook

/**
 * Registers a webhook URL for all event types. The URL will be validated via CRC request before saving. In case the validation failed, returns comprehensive error message to the requester.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/subscribe-account-activity/api-reference/aaa-premium#post-account-activity-all-env-name-webhooks)
 *
 * @param envName Environment name.
 * @param url Encoded URL for the callback endpoint.
 * @param options Optional. Custom parameters of this request.
 * @receiver [AccountActivity] endpoint instance.
 * @return [JsonObjectApiAction] for [Webhook.Model] model.
 */
fun AccountActivity.registerWebhook(
    envName: String,
    url: String,
    vararg options: Option
) = client.session.post("/1.1/account_activity/all/$envName/webhooks.json") {
    formBody(
        "url" to url,
        *options
    )

}.jsonObject { Webhook.Model(it, client) }
