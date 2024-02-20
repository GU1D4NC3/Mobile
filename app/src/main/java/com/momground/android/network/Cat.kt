package com.momground.android.network

import kotlinx.serialization.Serializable

@Serializable
data class Cat(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)
