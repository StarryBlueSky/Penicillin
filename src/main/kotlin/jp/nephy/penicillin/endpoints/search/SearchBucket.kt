package jp.nephy.penicillin.endpoints.search

import jp.nephy.penicillin.core.request.EnumRequestParameter

/**
 * Created on 2019/07/07.
 */

enum class SearchBucket(override val value: String): EnumRequestParameter {
    Day("day"),
    Hour("hour"),
    Minute("minute")
}