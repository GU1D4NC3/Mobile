package com.momground.android.data

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NewsItem2(
    val id: Int,
    var newsImage: String,
    val quizType: String,
    val quizDetail: String,
    val quizAnswer: String,
    val quizTitle: String,
    val newsTitle: String,
    val newsDetail: String,
    val quizChoice: String,
    val quizDescription: String,
    val createdAt: String?,
    val updatedAt: String?,
    val deletedAt: String?
)