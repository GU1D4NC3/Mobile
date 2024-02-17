package com.momground.android.data

object User {
    val user = User

    internal var isLogin = false

    internal var uuid: String = ""
    internal var name: String = ""
    internal var age: Int = 0

    fun clear(){
        isLogin = false
        uuid = ""
        name = ""
    }
}