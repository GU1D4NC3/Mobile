package com.momground.android.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NewsItem3(
    @SerializedName("id")
    val id: Int,
    @SerializedName("news_image")
    var newsImg: String,
    @SerializedName("quiz_type")
    val quizType: String,
    @SerializedName("quiz_detail")
    val quizDetail: String,
    @SerializedName("quiz_answer")
    val quizAnswer: String,
    @SerializedName("quiz_title")
    val quizTitle: String,
    @SerializedName("news_title")
    val newsTitle: String,
    @SerializedName("news_detail")
    val newsDetail: String,
    @SerializedName("quiz_choice")
    val quizChoice: String,
    @SerializedName("quiz_description")
    val quizDescription: String,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: String?
): java.io.Serializable { // 객체 직렬화 : 인텐트로 객체를 넘기기 위해서 직렬화를 해줘야 함
    constructor() : this(0,"","","","","","","","","","","","")
}