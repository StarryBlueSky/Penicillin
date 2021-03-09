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

@file:Suppress("Unused")

package blue.starry.penicillin.extensions.endpoints

import blue.starry.penicillin.core.request.action.ApiAction
import blue.starry.penicillin.core.response.JsonObjectResponse
import blue.starry.penicillin.endpoints.Lists
import blue.starry.penicillin.endpoints.lists.create
import blue.starry.penicillin.endpoints.lists.members
import blue.starry.penicillin.endpoints.lists.show
import blue.starry.penicillin.extensions.DelegatedAction
import blue.starry.penicillin.extensions.cursor.untilLast
import blue.starry.penicillin.extensions.models.plusAssign
import blue.starry.penicillin.models.TwitterList
import kotlinx.coroutines.flow.toList

/**
 * Clones this list. The name, description and mode are copied to new list.
 *
 * @param sourceId Source list id.
 *
 * @return New [ApiAction] for new list response.
 */
public fun Lists.clone(sourceId: Long): ApiAction<JsonObjectResponse<TwitterList>> = DelegatedAction {
    val sourceList = show(sourceId).execute()
    val sourceMembers = members(sourceId).untilLast("count" to 5000)
    val newList = create(sourceList.result.name, mode = sourceList.result.mode, description = sourceList.result.description).execute().result
    newList += sourceMembers.toList()

    show(newList.id).execute()
}
