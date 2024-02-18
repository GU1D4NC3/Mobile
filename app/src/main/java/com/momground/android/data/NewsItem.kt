package com.momground.android.data

data class NewsItem(
    val title: String,
    val category: String,
    val content: String,
    val author: String,
    var cover_url: String,
    val question: String,
    val questionType: String,
    val choices: String,
    val answer: String,
    val done: Boolean
)