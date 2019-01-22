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

package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.action.session
import jp.nephy.penicillin.core.response.StreamResponse
import jp.nephy.penicillin.core.streaming.handler.StreamHandler
import jp.nephy.penicillin.core.streaming.listener.StreamListener
import kotlinx.coroutines.*
import kotlinx.coroutines.io.jvm.javaio.toInputStream
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.io.core.Closeable
import mu.KotlinLogging
import kotlin.coroutines.CoroutineContext

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(response: StreamResponse<L, H>, private val handler: H): Closeable, CoroutineScope {
    var result = response
        private set
    
    val job = Job()
    private val mutex = Mutex()
    private object Dummy

    override val coroutineContext: CoroutineContext
        get() = result.action.session.coroutineContext
    
    private val logger = KotlinLogging.logger("Penicillin.StreamProcessor")
    
    suspend fun await(autoReconnect: Boolean = true) = apply {
        mutex.withLock(Dummy) {
            use {
                while (job.isActive) {
                    try {
                        launch {
                            handler.listener.onConnect()
                        }
                        
                        result.response.content.toInputStream(job).bufferedReader().useLines { lines ->
                            for (content in lines) {
                                launch {
                                    when {
                                        content.startsWith("{") -> {
                                            handler.handle(content.toJsonObject(), this)
                                        }
                                        content.isBlank() -> {
                                            handler.listener.onHeartbeat()
                                        }
                                        else -> {
                                            val length = content.toIntOrNull()
                                            if (length != null) {
                                                handler.listener.onLength(length)
                                            } else {
                                                handler.listener.onUnknownData(content)
                                            }
                                        }
                                    }
                                }

                                launch {
                                    handler.listener.onRawData(content)
                                }
                            }
                        }

                        launch {
                            handler.listener.onDisconnect()
                        }

                        if (!autoReconnect) {
                            break
                        }

                        while (job.isActive) {
                            try {
                                result.close()
                                result = result.action.request.stream<L, H>().await()
                                break
                            } catch (e: CancellationException) {
                                break
                            } catch (e: Throwable) {
                                logger.error(e) { LocalizedString.ExceptionInAsyncBlock }

                                delay(result.action.session.option.retryInMillis)
                                continue
                            }
                        }
                    } catch (e: CancellationException) {
                        break
                    } catch (e: Throwable) {
                        logger.error(e) { LocalizedString.ExceptionInAsyncBlock }
                    }
                }
            }
        }
    }

    override fun close() {
        result.close()
        job.cancelChildren()
    }
}
