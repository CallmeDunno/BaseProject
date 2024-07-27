package com.example.base.extensions

import android.os.Handler
import android.os.Looper

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

internal fun runDelay(long: Long, handle: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({ handle.invoke() }, long)
}