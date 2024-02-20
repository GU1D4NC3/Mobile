package com.momground.android.network

import android.content.ContentValues.TAG
import android.util.Log
import com.momground.android.data.NewsItem2
import com.momground.android.data.Response
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class NetworkRepository {
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun requestNewsById(id: Int): Response<NewsItem2> {
        val dataResponse: Response<NewsItem2> = Json.decodeFromString(requestKtorIo(id))
        return dataResponse
    }

    suspend fun requestKtorIo(id: Int): String =
        withContext(Dispatchers.IO) {
            val url = NetworkConfig.NEWS+"?id=$id"
            val response: HttpResponse = client.get(url)
            val responseStatus = response.status
            Log.d(TAG, "requestKtorIo: $responseStatus")

            if (responseStatus == HttpStatusCode.OK) {
                response.readText()
            } else {
                "error: $responseStatus"
            }
        }

    inline fun <reified T> decodeDataResponse(jsonString: String): Response<NewsItem2> {
        return Json.decodeFromString<Response<NewsItem2>>(jsonString)
    }


    fun closeClient() {
        client.close()
    }
}