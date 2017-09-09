package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class GeoSearchModel(val json: JsonElement) {
    val query by json.byNullableObject // {"url": "https://api.twitter.com/1.1/geo/search.json?query=Tokyo", "type": "search", "params": {"accuracy": 0.0, "granularity": "neighborhood", "query": "Tokyo", "autocomplete": false, "trim_place": false}}
    val result by json.byNullableObject // {"places": [{"id": "a56612250c754f23", "url": "https://api.twitter.com/1.1/geo/id/a56612250c754f23.json", "place_type": "admin", "name": "東京", "full_name": "日本 東京", "country_code": "JP", "country": "日本", "contained_within": [{"id": "06ef846bfc783874", "url": "https://api.twitter.com/1.1/geo/id/06ef846bfc783874.json", "place_type": "country", "name": "日本", "full_name": "日本", "country_code": "JP", "country": "日本", "centroid": [138.33054107905528, 36.260548400000005], "bounding_box": {"type": "Polygon", "coordinates": [[[122.9040343, 24.0133434], [122.9040343, 45.562897], [153.9976966, 45.562897], [153.9976966, 24.0133434], [122.9040343, 24.0133434]]]}, "attributes": {}}], "centroid": [139.4914357589862, 35.699818500000006], "bounding_box": {"type": "Polygon", "coordinates": [[[138.942847, 24.224701], [138.942847, 35.89849], [153.986676, 35.89849], [153.986676, 24.224701], [138.942847, 24.224701]]]}, "attributes": {}}]}
}

class GeoSearch(oauth: OAuthRequestHandler) : AbsOAuthGet<GeoSearchModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/geo/search.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
