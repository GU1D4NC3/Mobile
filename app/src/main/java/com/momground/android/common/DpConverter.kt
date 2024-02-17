@file:JvmName("DpConverter")
package com.momground.android.common

import android.content.Context
import android.util.TypedValue


fun Context.convertDpToPixel(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics).toInt()
}