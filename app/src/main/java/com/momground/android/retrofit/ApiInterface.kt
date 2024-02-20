package com.momground.android.retrofit

import com.momground.android.data.NewsItem2
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("services")
    fun getServices() : Call<ServicesSetterGetter>
    @GET("/news/id")
    fun getNewsById(
        @Query("id") id: Int
    ) : Call<NewsItem2>

}