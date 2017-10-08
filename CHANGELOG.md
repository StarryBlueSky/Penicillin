#### 1.2.1 (2017-10-08)

 * Streaming API endpoints now support type-safety to Streaming Listener. (e.g. when you use #getUserStream(), you may no longer use ISampleStreamListener)
 * Fix the issue that RT does not get full text with Status#fullText().
 * Support replies count tracking in LivePipeline API.
 * Add AbsStreamingParser#onClose {}. It's called when stream gets disconnected.
 
#### 1.2.0 (2017-09-21)

 * Add latest PAPIs such as Live Pipelines API, Moment Guide API, Trend Plus API.
 * Update request headers in order to support latest APIs.
 * Add Twitter API error classes.
 * In Json Converter, more safety parsing is available.

#### 1.1.0 (2017-09-19)

 * Add some Private API endpoints.
 * Add enum class for Official-Client.
 * Add some fields in `User` model.
 * Improve Client & Session instance.
 * `Response*` no longer has Client instance.
 * In Twitter API error handling, `UnsupportedOperationException` is catched from now.

#### 1.0.1 (2017-09-17)

 * Improve Twitter API error handling.
 * In UserStream, fixed the bug that "<", ">", and "&" are not unescaped to.

#### 1.0.0 (2017-09-17)

 * Initial release on Maven Central Repo.
