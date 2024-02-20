package com.momground.android.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.momground.android.network.NetworkConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val contentType = "application/json".toMediaType()
    val client = Retrofit
        .Builder()
        .baseUrl(NetworkConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    fun getInstance(): Retrofit{
        return client
    }
}