#### 2.0.0 (2018-04-24)

 * Support async api execusion.
 * Refactoring all classes.

#### 1.4.1 (2018-03-27)

 * Update Json.kt to v1.4.

#### 1.4.0 (2018-02-21)

 * Change internal json binding to [Json.kt](https://github.com/NephyProject/Json.kt) from Kotson.

#### 1.3.1 (2017-12-12)

 * Now IListener interfaces has default implementation.
 * Add JsonConvertLambdaDelegate.

#### 1.3.0 (2017-11-14)

 * Add cursor endpoint support.
 * Better ResponseObject, ResponseList, ResponseStream.
 * Fix the issue that HTML special characters are not unescaped.
 * Update API request headers.
 * You may now tweet 280 characters.
 * Fix statuses.lookup's arguments.
 * Improved performance.

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
