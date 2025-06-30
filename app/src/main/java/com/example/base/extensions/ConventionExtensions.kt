package com.example.base.extensions

import android.os.Handler
import android.os.Looper
import android.util.Log

internal fun Boolean?.isTrue(): Boolean = this == true

internal fun Boolean?.isFalse(): Boolean = this == false

internal fun Boolean?.isFalseOrNull(): Boolean = this == false || this == null

internal fun tryBlock(func: () -> Unit): Exception? {
    return try {
        func.invoke()
        null
    } catch (e: Exception) {
        e
    }
}

internal fun <T> T?.ifNotNull(blockNotNull: (T) -> Unit = {}, blockNull: () -> Unit = {}) {
    if (null != this) blockNotNull(this)
    else blockNull()
}

internal fun runDelay(long: Long, handle: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({ handle.invoke() }, long)
}

internal fun executionTime(func: () -> Unit) {
    val start = getMilliseconds()
    func.invoke()
    Log.w("Dunno", "Execution Time: ${getMilliseconds() - start}")
}