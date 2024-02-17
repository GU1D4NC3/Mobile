package com.momground.android.common


import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.momground.android.data.User
import com.momground.android.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.page_in_up, R.anim.none)
        }
    }

    fun Context.initStatusBar(view: View){
        view.setPadding(
            0,
            this.statusBarHeight(),
            0,
            0
        )
    }

    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE,R.anim.none, R.anim.page_in_down)
        }
    }

    open fun isLogin(): Boolean{
        return User.isLogin
    }
    open fun isNoneLogin(): Boolean{
        return !User.isLogin
    }
}