package com.momground.android.network

/**
 * 흠....
 * 여기에서 URL을 설정하고..
 * 푸시할때는 제외해주면 되겠네요..
 *
 * https://test.accx.dev/news/id?id=1
 */
object NetworkConfig {
    // dev variant는 스테이징으로 고정해놓도록 해요.
    const val BASE_URL = "https://test.accx.dev"

    const val POSTS = "$BASE_URL/posts"
    const val NEWS = "$BASE_URL/news/id"
}