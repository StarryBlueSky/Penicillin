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

package blue.starry.penicillin.core.request.action

import blue.starry.penicillin.core.exceptions.PenicillinException
import blue.starry.penicillin.core.request.ApiRequest
import blue.starry.penicillin.core.session.ApiClient
import kotlinx.coroutines.CancellationException

/**
 * Represents lazy [ApiRequest] invoker.
 */
public interface ApiAction<R> {
    /**
     * Current [ApiClient] instance.
     */
    public val client: ApiClient

    /**
     * Current lazy [ApiRequest] instance.
     */
    public val request: ApiRequest

    /**
     * Executes [ApiAction] and returns its result.
     * This function is suspendable.
     *
     * @return Api result as [R].
     *
     * @throws PenicillinException General Penicillin exceptions.
     * @throws CancellationException Thrown when coroutine scope is cancelled.
     */
    public suspend fun execute(): R
}
