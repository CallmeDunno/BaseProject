package com.example.base.helper

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionHelper {
    fun checkPermission(permission: String?, context: Context?): Boolean {
        return if (context != null) {
            ActivityCompat.checkSelfPermission(context, permission!!) == PackageManager.PERMISSION_GRANTED
        } else false
    }

    fun checkPermissionList(listPermission: Array<String>, context: Context?): Boolean {
        for (per in listPermission) {
            val allow = ActivityCompat.checkSelfPermission(context!!, per) == PackageManager.PERMISSION_GRANTED
            if (!allow) return false
        }
        return true
    }

//    fun checkStoragePermission(context: Context) : Boolean  {
//        return if (Build.VERSION.SDK_INT < 33) checkPermissionList(arrayOf(Constant.PER_WRITE_STORAGE), context) else true
//    }
//
//    fun requestStoragePermission(activity: Activity) {
//        if (Build.VERSION.SDK_INT < 33) activity.requestPermissions(arrayOf(Constant.PER_WRITE_STORAGE), Constant.PER_STORAGE_CODE)
//    }

}