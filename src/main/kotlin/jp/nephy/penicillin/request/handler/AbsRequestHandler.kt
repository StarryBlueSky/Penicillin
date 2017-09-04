package jp.nephy.penicillin.request.handler

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import jp.nephy.penicillin.request.toParamList
import java.net.URL

abstract class AbsRequestHandler {
    protected fun httpGet(url: URL, header: Map<String,String>, data: Map<String,String>?=null): Triple<Request,Response,Result<String,FuelError>> {
        return Fuel.get(url.toString(), data?.toParamList()).header(header).responseString()
    }

    protected fun httpPost(url: URL, header: Map<String,String>, data: Map<String,String>?=null): Triple<Request,Response,Result<String,FuelError>> {
        return Fuel.post(url.toString(), data?.toParamList()).header(header).responseString()
    }

    protected fun httpDelete(url: URL, header: Map<String,String>, data: Map<String,String>?=null): Triple<Request,Response,Result<String,FuelError>> {
        return Fuel.delete(url.toString(), data?.toParamList()).header(header).responseString()
    }
}
