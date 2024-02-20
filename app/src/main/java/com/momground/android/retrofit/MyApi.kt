package com.momground.android.retrofit

import com.momground.android.data.NewsItem2
import com.momground.android.data.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface MyApi {
    // 푸시 메세지 요청
    @POST("/push")
    suspend fun sendPushMessage(
        @Body pushBody: PushBody
    )

    @GET("/news/id")
    fun getNewsById(@Query("id") id: Int): Call<Response<NewsItem2>>
}

data class PushBody(
    val token: String, // 메세지를 받는 사람의 fcm 토큰
    val from: String, // 보내는 사람
    val message: String // 메세지
)