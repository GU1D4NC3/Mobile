package com.momground.android.common

import androidx.fragment.app.Fragment
import com.momground.android.data.User

open class BaseFragment: Fragment(){
    open fun isLogin(): Boolean{
        return User.isLogin
    }
    open fun isNoneLogin(): Boolean{
        return !User.isLogin
    }
}