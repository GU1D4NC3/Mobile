package com.momground.android.retrofit


class RetrofitRepository {
    private val retrofit = RetrofitInstance.getInstance().create(MyApi::class.java)

    fun getNewsById(id: Int) = retrofit.getNewsById(id)
}
