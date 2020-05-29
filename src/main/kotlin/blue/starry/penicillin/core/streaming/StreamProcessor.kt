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

@file:Suppress("UNUSED")

package blue.starry.penicillin.core.streaming

import blue.starry.jsonkt.toJsonObject
import io.ktor.utils.io.core.Closeable
import io.ktor.utils.io.jvm.javaio.toInputStream
import blue.starry.penicillin.core.i18n.LocalizedString
import blue.starry.penicillin.core.request.action.unescapeHTML
import blue.starry.penicillin.core.response.StreamResponse
import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.core.streaming.handler.StreamHandler
import blue.starry.penicillin.core.streaming.listener.StreamListener
import blue.starry.penicillin.extensions.execute
import blue.starry.penicillin.extensions.session
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import mu.KotlinLogging
import kotlin.coroutines.CoroutineContext

/**
 * The processor which works on Streaming Api parsing.
 */
class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(
    /**
     * Current [ApiClient].
     */
    val client: ApiClient,

    private var result: StreamResponse<L, H>,
    private val handler: H
): Closeable, CoroutineScope {
    private val logger = KotlinLogging.logger("Penicillin.StreamProcessor")
    
    private val mutex = Mutex()
    private object Dummy

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = result.session.coroutineContext + job

    /**
     * Awaits until streaming ends, or client disconnects.
     * This operation is suspendable.
     */
    suspend fun await(reconnect: Boolean = true, job: CompletableJob? = null): StreamProcessor<L, H> {
        job?.also {
            this.job = it
        }

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
                result = result.action.request.stream<L, H>().execute()
                break
            } catch (e: Throwable) {
                logger.error(e) { LocalizedString.ExceptionInAsyncBlock }

                delay(result.session.option.retryInMillis)
                continue
            }
        }
    }

    /**
     * Aborts active streaming connection.
     */
    override fun close() {
        job.cancel()
    }
}
