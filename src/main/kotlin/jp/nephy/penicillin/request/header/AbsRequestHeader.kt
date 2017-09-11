package jp.nephy.penicillin.request.header

import okhttp3.Headers

abstract class AbsRequestHeader {
    protected val builder = Headers.Builder()

    fun get() = builder.build()!!
}
