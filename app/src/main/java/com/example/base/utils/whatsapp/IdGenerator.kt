package com.example.base.utils.whatsapp

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.base.Constant
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object IdGenerator {

    fun generateIdFromUrl(context: Context, url: String): String {
        val randomIdentifier = LocalStorageUtils.readData(context, Constant.RANDOM_IDENTIFIER_FOR_ADDING_STICKER_TO_WHATSAPP)
        return getFolderNameFromUrl(url).lowercase() + randomIdentifier
    }

    fun getFolderNameFromUrl(url: String): String {
        return url.substringBeforeLast('/').substringAfterLast('/')
    }

    fun getImageUri(context: Context, bitmap: Bitmap, name: String): Uri? {
        val cachePath = context.cacheDir
        val bitmapFile = File(cachePath, "${name}_haha")

        try {
            val stream = FileOutputStream(bitmapFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
            return FileProvider.getUriForFile(context, context.packageName + ".provider", bitmapFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

}