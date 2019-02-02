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

package jp.nephy.penicillin.extensions.endpoints

import jp.nephy.penicillin.endpoints.Lists
import jp.nephy.penicillin.endpoints.lists.create
import jp.nephy.penicillin.endpoints.lists.members
import jp.nephy.penicillin.endpoints.lists.show
import jp.nephy.penicillin.extensions.DelegatedAction
import jp.nephy.penicillin.extensions.await
import jp.nephy.penicillin.extensions.cursor.allUsers
import jp.nephy.penicillin.extensions.cursor.untilLast
import jp.nephy.penicillin.extensions.models.plusAssign

fun Lists.clone(sourceId: Long) = DelegatedAction {
    val sourceList = show(sourceId).await()
    val sourceMembers = members(sourceId).untilLast("count" to 5000).allUsers
    val newList = create(sourceList.result.name, mode = sourceList.result.mode, description = sourceList.result.description).await().result
    newList += sourceMembers

    show(newList.id).await()
}
