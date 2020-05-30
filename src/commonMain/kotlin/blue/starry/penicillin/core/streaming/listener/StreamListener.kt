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

package blue.starry.penicillin.core.streaming.listener

import blue.starry.jsonkt.JsonObject
import blue.starry.penicillin.core.streaming.handler.StreamHandler

/**
 * A common event model interface for [StreamHandler].
 */
interface StreamListener {
    /**
     * Called when streaming is connected.
     */
    suspend fun onConnect() {}
    /**
     * Called when streaming is disconnected.
     */
    suspend fun onDisconnect(cause: Throwable?) {}

    /**
     * Called when streaming sends heartbeat.
     */
    suspend fun onHeartbeat() {}
    /**
     * Called when streaming sends length.
     */
    suspend fun onLength(length: Int) {}

    /**
     * Called when any payloads are received.
     */
    suspend fun onAnyJson(json: JsonObject) {}
    /**
     * Called when any data are connected.
     */
    suspend fun onRawData(data: String) {}
    /**
     * Called when unhandled payloads are detected.
     */
    suspend fun onUnhandledJson(json: JsonObject) {}
    /**
     * Called when unhandled data are detected.
     */
    suspend fun onUnknownData(data: String) {}
}
