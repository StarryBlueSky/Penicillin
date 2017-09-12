package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.model.TrendArea
import jp.nephy.penicillin.parameters.TrendExclude
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.result.TrendsPlace

class Trend(private val client: Client) {
    @GET
    fun getAvailableAreas(vararg options: Pair<String, String?>): ResponseList<TrendArea> {
        return client.session.new()
                .url("/trends/available.json")
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getClosestAreas(lat: Float, long: Float, vararg options: Pair<String, String?>): ResponseList<TrendArea> {
        return client.session.new()
                .url("/trends/closest.json")
                .param("lat" to lat)
                .param("long" to long)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getTrends(id: Long, exclude: TrendExclude?=null, vararg options: Pair<String, String?>): ResponseList<TrendsPlace> {
        return client.session.new()
                .url("/trends/place.json")
                .param("id" to id)
                .param("exclude" to when (exclude) {
                    TrendExclude.Hashtags -> "hashtags"
                    else -> null
                })
                .params(*options)
                .get()
                .getResponseList()
    }
}