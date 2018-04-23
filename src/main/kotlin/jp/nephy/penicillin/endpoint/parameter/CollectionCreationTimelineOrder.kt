package jp.nephy.penicillin.endpoint.parameter

enum class CollectionCreationTimelineOrder(val value: String) {
    OrderAdded("tweet_chron"), OldestFirst("curation_reverse_chron"), MostRecentFirst("tweet_reverse_chron")
}
