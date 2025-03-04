package com.example.base.utils

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(private val sharedPre: SharedPreferences) {

    fun putString(key: String, value: String) {
        sharedPre.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String): String? {
        return sharedPre.getString(key, null)
    }

    fun getInt(key: String): Int {
        return sharedPre.getInt(key, 0)
    }

    fun putInt(key: String, i: Int) {
        sharedPre.edit().putInt(key, i).apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPre.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun getBoolean(key: String): Boolean {
        return sharedPre.getBoolean(key, false)
    }

    fun removeKey(key: String) {
        sharedPre.edit().remove(key).apply()
    }

    fun clear() {
        sharedPre.edit().apply {
            clear()
            apply()
        }
    }

}