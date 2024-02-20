package com.momground.android.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsItem2(
    @SerialName("id")
    val id: Int,
    @SerialName("news_image")
    var newsImage: String,
    @SerialName("quiz_type")
    val quizType: String,
    @SerialName("quiz_detail")
    val quizDetail: String,
    @SerialName("quiz_answer")
    val quizAnswer: String,
    @SerialName("quiz_title")
    val quizTitle: String,
    @SerialName("news_title")
    val newsTitle: String,
    @SerialName("news_detail")
    val newsDetail: String,
    @SerialName("quiz_choice")
    val quizChoice: String,
    @SerialName("quiz_description")
    val quizDescription: String,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("deleted_at")
    val deletedAt: String?
)