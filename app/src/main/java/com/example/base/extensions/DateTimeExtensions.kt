package com.example.base.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat") internal fun formatDateTime(): String {
    val formatter = SimpleDateFormat("dd_MM_yyyy_hh_mm_ss")
    return formatter.format(Date())
}

@SuppressLint("SimpleDateFormat") internal fun formatDateTime(time: Long): String {
    val formatter = SimpleDateFormat("yyyy/MM/dd | hh:mm")
    return formatter.format(time)
}

internal fun formatMilliseconds(milliseconds: Long): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
    return String.format("%02d:%02d", minutes, seconds)
}

internal fun formatMilliseconds2(milliseconds: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60
    return String.format("%02dh%02dm%02ds", hours, minutes, seconds)
}

internal fun formatTime(milliseconds: Long) : String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(Date(milliseconds))
}