package com.momground.android.data

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val status: String,
    val data: T
)