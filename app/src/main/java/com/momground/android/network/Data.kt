package com.momground.android.network

import kotlinx.serialization.Serializable

@Serializable
data class Data(val title: String, val desc: String, val content: String)

fun main() {
    /*
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        routing {
            get("/getData") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, "id parameter missing")
                // 여기서는 간단히 id를 사용하여 더미 데이터를 만들어 반환합니다.
                val data = Data("Title $id", "Description $id", "Content $id")
                call.respond(data)
            }
        }
    }.start(wait = true)

     */
}