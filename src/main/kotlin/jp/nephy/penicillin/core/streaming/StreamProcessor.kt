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
import jp.nephy.penicillin.core.request.action.unescapeHTML
import jp.nephy.penicillin.core.response.StreamResponse
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.streaming.handler.StreamHandler
import jp.nephy.penicillin.core.streaming.listener.StreamListener
import jp.nephy.penicillin.extensions.await
import jp.nephy.penicillin.extensions.session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.io.jvm.javaio.toInputStream
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.io.core.Closeable
import kotlinx.io.core.use
import mu.KotlinLogging
import kotlin.coroutines.CoroutineContext

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(val client: ApiClient, private var result: StreamResponse<L, H>, private val handler: H): Closeable, CoroutineScope {
    private val logger = KotlinLogging.logger("Penicillin.StreamProcessor")
    
    private val mutex = Mutex()
    private object Dummy

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = result.session.coroutineContext + job
    
    suspend fun await(reconnect: Boolean = true): StreamProcessor<L, H> {
        return apply {
            mutex.withLock(Dummy) {
                use {
                    loop(reconnect)
                }
            }
        }
    }
    
    private suspend fun loop(reconnect: Boolean) {
        while (job.isActive) {
            try {
                handle()
            } catch (e: Throwable) {
                logger.error(e) { LocalizedString.ExceptionInAsyncBlock }
                
                if (!reconnect) {
                    break
                }
                
                delay(result.session.option.retryInMillis)
                
                reconnect()
            }
        }
    }
    
    private fun handle() {
        val loopJob = Job()
        
        launch {
            handler.listener.onConnect()
        }
        
        result.response.content.toInputStream(loopJob).bufferedReader().useLines { lines -> 
            for (line in lines) {
                handleLine(line)
            }
        }
        
        loopJob.invokeOnCompletion {
            launch {
                handler.listener.onDisconnect(it)
            }
        }
    }
    
    private fun handleLine(line: String) {
        val content = line.unescapeHTML()

        launch {
            when {
                content.startsWith('{') -> {
                    handler.handle(content.toJsonObject())
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
    
    private suspend fun reconnect() {
        while (job.isActive) {
            try {
                result.close()
                result = result.action.request.stream<L, H>().await()
                break
            } catch (e: Throwable) {
                logger.error(e) { LocalizedString.ExceptionInAsyncBlock }

                delay(result.session.option.retryInMillis)
                continue
            }
        }
    }

    override fun close() {
        result.close()
        job.cancel()
    }
}
