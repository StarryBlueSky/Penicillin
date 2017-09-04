package jp.nephy.penicillin.request.header

import jp.nephy.penicillin.request.toParamString

abstract class AbsRequestHeader {
    protected val _header: MutableMap<String,String> = mutableMapOf()

    fun get(): MutableMap<String,String> {
        return _header
    }

    fun setLength(data: Map<String,String>?=null) {
        if (data == null) {
            return
        }
        _header["Content-Length"] = data.toParamString().length.toString()
    }
}
