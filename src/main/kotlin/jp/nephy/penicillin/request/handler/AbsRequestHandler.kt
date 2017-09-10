package jp.nephy.penicillin.request.handler

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.result.Result
import jp.nephy.penicillin.request.header.AbsRequestHeader
import jp.nephy.penicillin.request.toParamList
import java.net.URL

abstract class AbsRequestHandler {
    protected fun httpGet(url: URL, header: AbsRequestHeader, data: Map<String,String>?=null): Triple<Request,Response,Result<String,FuelError>> {
        return Fuel.get(url.toString(), data?.toParamList())
                .header(header.get())
                .responseString()
    }

    protected fun httpPost(url: URL, header: AbsRequestHeader, data: Map<String,String>?=null): Triple<Request,Response,Result<String,FuelError>> {
        return Fuel.post(url.toString(), data?.toParamList())
                .header(header.get())
                .responseString()
    }

    protected fun httpDelete(url: URL, header: AbsRequestHeader, data: Map<String,String>?=null): Triple<Request,Response,Result<String,FuelError>> {
        return Fuel.delete(url.toString(), data?.toParamList())
                .header(header.get())
                .responseString()
    }

    protected fun httpUpload(url: URL, header: AbsRequestHeader, file: ByteArray, data: Map<String,String>?=null): Triple<Request,Response,Result<String,FuelError>> {
        return Fuel.upload(url.toString(), parameters = data?.toParamList())
                .header(header.get())
                .blob { _, _ ->
                    Blob("blob", file.size.toLong(), { file.inputStream() })
                }
                .name {"media"}
                .responseString()
    }
}
