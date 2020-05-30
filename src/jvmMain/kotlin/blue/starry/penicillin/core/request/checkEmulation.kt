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

package blue.starry.penicillin.core.request

import blue.starry.penicillin.core.emulation.EmulationMode
import blue.starry.penicillin.core.exceptions.PenicillinException
import blue.starry.penicillin.core.i18n.LocalizedString
import blue.starry.penicillin.endpoints.PrivateEndpoint
import blue.starry.penicillin.extensions.session

actual fun ApiRequestBuilder.checkEmulation() {
    if (session.option.skipEmulationChecking) {
        return
    }

    val trace = Thread.currentThread().stackTrace.find {
        it.className.startsWith("blue.starry.penicillin.endpoints")
    } ?: return
    val javaClass = javaClass.classLoader.loadClass(trace.className)
    val method = javaClass.methods.find { it.name == trace.methodName } ?: return

    apiRequestBuilderLogger.trace { "Endpoint: ${javaClass.canonicalName}#${method.name}" }

    val annotation = method.getAnnotation(PrivateEndpoint::class.java) ?: return
    if (session.option.emulationMode == EmulationMode.None || (annotation.modes.isNotEmpty() && session.option.emulationMode !in annotation.modes)) {
        throw PenicillinException(LocalizedString.PrivateEndpointRequiresOfficialClientEmulation)
    }
}
