package com.momground.android.common

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.momground.android.data.User

open class BaseFragment: Fragment(){
    open fun isLogin(): Boolean{
        return User.isLogin
    }
    open fun isNoneLogin(): Boolean{
        return !User.isLogin
    }

    protected open fun sendEvent(s: String?, @Nullable bundle: Bundle?): BaseFragment {
        // Analytics.send { analytics -> analytics.logEvent(s, bundle) }
        return this
    }

    protected open fun sendEvent(s: String?): BaseFragment {
        // Analytics.send { analytics -> analytics.logEvent(s, null) }
        return this
    }
}