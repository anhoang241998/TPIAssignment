package com.annguyenhoang.tpiassignment.utils

import android.content.Context
import android.util.TypedValue

fun Context.toPixel(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    )
}