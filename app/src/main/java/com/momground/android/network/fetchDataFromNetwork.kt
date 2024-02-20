package com.momground.android.network

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

suspend fun fetchDataFromNetwork(): String {
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    return try {
        val id = "1"
        client.get(NetworkConfig.NEWS+"?id=$id")
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    } finally {
        client.close()
    }
}