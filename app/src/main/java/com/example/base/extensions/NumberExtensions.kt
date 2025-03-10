package com.example.base.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import java.text.DecimalFormat

internal fun formatDecimal(number: Double, decimalPlaces: Int): String {
    val pattern = "#." + "0".repeat(decimalPlaces)
    val decimalFormat = DecimalFormat(pattern)
    return decimalFormat.format(number)
}

internal fun dp2px(dpVal: Float): Int {
    val r = Resources.getSystem()
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, r.displayMetrics).toInt()
}

internal fun sp2px(spVal: Float): Int {
    val r = Resources.getSystem()
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, r.displayMetrics).toInt()
}

internal fun dpToPx(dp: Int, context: Context): Float {
    val density = context.resources.displayMetrics.density
    return (dp * density)
}

internal fun dipToPx(dpValue: Float): Float {
    val scale: Float = Resources.getSystem().displayMetrics.density
    return (dpValue * scale + 0.5f)
}