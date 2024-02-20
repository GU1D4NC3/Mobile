package com.momground.android.network

import com.momground.android.data.Response
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

class MyClass {
    suspend fun getCatFromApi(): List<Cat> {
        val client = HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
        }

        return client.get<List<Cat>>("https://api.thecatapi.com/v1/images/search?limit=5")
    }

    suspend fun getNewsById(id: Int): Response<NewsItem2> {
        val client = HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
        }

        return client.get<Response<NewsItem2>>(LocalNetworkConfig.BASE_URL+"news/id?id=$id")
    }

    companion object {
        private val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
    }
}