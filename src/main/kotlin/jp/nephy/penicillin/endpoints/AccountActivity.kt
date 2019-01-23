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

import jp.nephy.penicillin.core.session.*
import jp.nephy.penicillin.models.Subscription
import jp.nephy.penicillin.models.Webhook

val ApiClient.accountActivity: AccountActivity
    get() = AccountActivity(this)

class AccountActivity(override val client: ApiClient): Endpoint

fun AccountActivity.registerWebhook(
    url: String,
    envName: String,
    vararg options: Option
) = client.session.post("/1.1/account_activity/all/$envName/webhooks.json") {
    body {
        form {
            add("url" to url, *options)
        }
    }
}.jsonObject<Webhook.Model>()

fun AccountActivity.listWebhooks(
    vararg options: Option
) = client.session.get("/1.1/account_activity/all/webhooks.json") {
    parameter(*options)
}.jsonObject<Webhook.List>()

fun AccountActivity.listWebhooks(
    envName: String,
    vararg options: Option
) = client.session.get("/1.1/account_activity/all/$envName/webhooks.json") {
    parameter(*options)
}.jsonArray<Webhook.Model>()

fun AccountActivity.triggerCRC(
    envName: String,
    webhookId: String,
    vararg options: Option
) = client.session.put("/1.1/account_activity/all/$envName/webhooks/$webhookId.json") {
    parameter(*options)
}.empty()

fun AccountActivity.deleteWebhook(
    envName: String,
    webhookId: String,
    vararg options: Option
) = client.session.delete("/1.1/account_activity/all/$envName/webhooks/$webhookId.json") {
    parameter(*options)
}.empty()

fun AccountActivity.subscribe(
    envName: String,
    vararg options: Option
) = client.session.post("/1.1/account_activity/all/$envName/subscriptions.json") {
    body {
        form {
            add(*options)
        }
    }
}.empty()

fun AccountActivity.subscriptionCount(
    vararg options: Option
) = client.session.get("/1.1/account_activity/all/subscriptions/count.json") {
    parameter(*options)
}.jsonObject<Subscription.Count>()

fun AccountActivity.checkSubscription(
    envName: String,
    vararg options: Option
) = client.session.get("/1.1/account_activity/all/$envName/subscriptions.json") {
    parameter(*options)
}.empty()

fun AccountActivity.listSubscriptions(
    envName: String,
    vararg options: Option
) = client.session.get("/1.1/account_activity/all/$envName/subscriptions/list.json") {
    parameter(*options)
}.jsonObject<Subscription.List>()

fun AccountActivity.unsubscribe(
    envName: String,
    vararg options: Option
) = client.session.delete("/1.1/account_activity/all/$envName/subscriptions.json") {
    parameter(*options)
}.empty()
