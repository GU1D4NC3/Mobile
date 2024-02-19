package com.momground.android.network

import android.util.Log
import com.momground.android.common.LocalNetworkConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.date.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HttpRequestHelper {
    companion object {
        val TAG: String = HttpRequestHelper::class.java.name
        const val APP_ID = "60edbdd55a875729c30f0ff2"
    }
    private val client: HttpClient = HttpClient(CIO)

    suspend fun requestKtorIo(): String =
        withContext(Dispatchers.IO) {
            val response: HttpResponse = client.get(LocalNetworkConfig.BASE_URL+"/facts/random")
            val responseStatus = response.status
            Log.d(TAG, "requestKtorIo: $responseStatus")

            if (responseStatus == HttpStatusCode.OK) {
                response.readText()
            } else {
                "error: $responseStatus"
            }
        }

    suspend fun requestKtorIoInDetail(): String =
        withContext(Dispatchers.IO) {
            // set HttpRequestBuilder
            val response: HttpResponse = client.request(LocalNetworkConfig.BASE_URL+"/data/api/user") {
                method = HttpMethod.Get
                headers {
                    append(HttpHeaders.Accept, "text/html")
                    append("app-id", APP_ID)
                    append(HttpHeaders.UserAgent, "ktor client")
                }
                cookie(name = "user_name", value = "soobin park", expires = GMTDate(
                    seconds = 0,
                    minutes = 0,
                    hours = 10,
                    dayOfMonth = 1,
                    month = Month.JULY,
                    year = 2022
                ))
            }
            val responseStatus = response.status
            Log.d(TAG, "requestKtorIo: $responseStatus")

            if (responseStatus == HttpStatusCode.OK) {
                response.readText()
            } else {
                "error: $responseStatus"
            }
        }
}