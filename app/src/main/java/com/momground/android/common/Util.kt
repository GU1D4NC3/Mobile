package com.momground.android.common

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.view.WindowCompat


internal fun Context.bottomNavigationHeight(): Int{
    var navigationBarHeight = 0
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceId > 0) { navigationBarHeight = resources.getDimensionPixelSize(resourceId)
    }
    return navigationBarHeight
}

internal fun Context.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Activity.setStatusBarBlack() {
    window.apply {
        setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    if(Build.VERSION.SDK_INT >= 30) {	// AP
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }
}