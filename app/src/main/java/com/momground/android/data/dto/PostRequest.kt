package com.momground.android.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val body: String,
    val title: String,
    val userId: Int
)
