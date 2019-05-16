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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package jp.nephy.penicillin.endpoints.statuses

import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.core.request.EndpointHost
import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.parameter
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Statuses
import jp.nephy.penicillin.models.Embed

/**
 * Returns a single Tweet, specified by either a Tweet web URL or the Tweet ID, in an [oEmbed](http://oembed.com/)-compatible format. The returned HTML snippet will be automatically recognized as an [Embedded Tweet](https://developer.twitter.com/web/embedded-tweets) when [Twitter's widget JavaScript is included on the page](https://developer.twitter.com/web/javascript/loading).
 * The oEmbed endpoint allows customization of the final appearance of an Embedded Tweet by setting the corresponding properties in HTML markup to be interpreted by Twitter's JavaScript bundled with the HTML response by default. The format of the returned markup may change over time as Twitter adds new features or adjusts its Tweet representation.
 * The Tweet fallback markup is meant to be cached on your servers for up to the suggested cache lifetime specified in the cache_age.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/get-statuses-oembed)
 * 
 * @param url The URL of the Tweet to be embedded.
 * @param maxWidth The maximum width of a rendered Tweet in whole pixels. A supplied value under or over the allowed range will be returned as the minimum or maximum supported width respectively; the reset width value will be reflected in the returned width property. Note that Twitter does not support the oEmbed maxheight parameter. Tweets are fundamentally text, and are therefore of unpredictable height that cannot be scaled like an image or video. Relatedly, the oEmbed response will not provide a value for height. Implementations that need consistent heights for Tweets should refer to the hide_thread and hide_media parameters below.
 * @param hideMedia When set to true, "t", or 1 links in a Tweet are not expanded to photo, video, or link previews.
 * @param hideThread When set to true, "t", or 1 a collapsed version of the previous Tweet in a conversation thread will not be displayed when the requested Tweet is in reply to another Tweet.
 * @param omitScript When set to true, "t", or 1 the <script> responsible for loading widgets.js will not be returned. Your webpages should include their own reference to widgets.js for use across all Twitter widgets including [Embedded Tweets](https://developer.twitter.com/web/embedded-tweets).
 * @param align Specifies whether the embedded Tweet should be floated left, right, or center in the page relative to the parent element.
 * @param related A list of Twitter usernames related to your content. This value will be forwarded to Tweet action intents if a viewer chooses to reply, like, or retweet the embedded Tweet.
 * @param lang Request returned HTML and a rendered Tweet in the specified [Twitter language supported by embedded Tweets](https://developer.twitter.com/web/overview/languages).
 * @param theme When set to dark, the Tweet is displayed with light text over a dark background.
 * @param linkColor Adjust the color of Tweet text links with a [hexadecimal color value](https://en.wikipedia.org/wiki/Web_colors#Hex_triplet).
 * @param widgetType Set to video to return a Twitter Video embed for the given Tweet.
 * @param dnt When set to true, the Tweet and its embedded page on your site are not used for purposes that include [personalized suggestions](https://support.twitter.com/articles/20169421) and [personalized ads](https://support.twitter.com/articles/20170405).
 * @param options Optional. Custom parameters of this request.
 * @receiver [Statuses] endpoint instance.
 * @return [JsonObjectApiAction] for [Embed] model.
 */
fun Statuses.embedFormat(
    url: String,
    maxWidth: Int? = null,
    hideMedia: Boolean? = null,
    hideThread: Boolean? = null,
    omitScript: Boolean? = null,
    align: EmbedAlign = EmbedAlign.Default,
    related: List<String>? = null,
    lang: String? = null,
    theme: EmbedTheme = EmbedTheme.Default,
    linkColor: String? = null,
    widgetType: EmbedWidgetType = EmbedWidgetType.Default,
    dnt: Boolean? = null,
    vararg options: Option
) = client.session.get("/oembed", EndpointHost.Publish) {
    authorizationType = AuthorizationType.None

    parameter(
        "url" to url,
        "maxwidth" to maxWidth,
        "hide_media" to hideMedia,
        "hide_thread" to hideThread,
        "omit_script" to omitScript,
        "align" to align.value,
        "related" to related?.joinToString(","),
        "lang" to lang,
        "theme" to theme.value,
        "link_color" to linkColor,
        "widget_type" to widgetType.value,
        "dnt" to dnt,
        *options
    )
}.jsonObject<Embed>()
