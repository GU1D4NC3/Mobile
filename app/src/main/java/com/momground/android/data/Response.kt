package com.momground.android.data

@kotlinx.serialization.Serializable
data class Response<T>(
    val status: String,
    val data: T
)