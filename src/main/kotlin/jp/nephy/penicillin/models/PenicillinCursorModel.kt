package jp.nephy.penicillin.models

import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.string

abstract class PenicillinCursorModel: PenicillinModel {
    val nextCursor by long("next_cursor")
    val nextCursorStr by string("next_cursor_str")
    val previousCursor by long("previous_cursor")
    val previousCursorStr by string("previous_cursor_str")
}
