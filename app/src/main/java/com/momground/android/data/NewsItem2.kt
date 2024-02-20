package com.momground.android.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsItem2(
    val id: Int,
    var newsImg: String,
    val quizDetail: String,
    val quizAnswer: String,
    val updatedAt: String?,
    val quizTitle: String,
    val newsTitle: String,
    val newsDetail: String,
    val quizChoice: String,
    val quizDescription: String,
    val createdAt: String?,
    val deletedAt: String?,
    val answer: String
)