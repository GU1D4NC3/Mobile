package com.momground.android.data

@kotlinx.serialization.Serializable
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
): java.io.Serializable { // 객체 직렬화 : 인텐트로 객체를 넘기기 위해서 직렬화를 해줘야 함
    constructor() : this(0,"","","","","","","","","","","","")
}