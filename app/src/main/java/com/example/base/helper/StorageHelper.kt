package com.example.base.helper

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.example.base.extensions.formatDateTime
import com.example.base.extensions.getMilliseconds
import com.example.base.extensions.tryBlock
import com.example.base.utils.UIState
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object StorageHelper {

    fun getFiles(context: Context, folder: String): ArrayList<String> {
        val paths = ArrayList<String>()
        val targetDir = createFolder(context, folder)

        if (targetDir.exists() && targetDir.isDirectory) {
            targetDir.listFiles()?.let { files ->
                val sortedFiles = files.sortedBy { it.lastModified() }
                sortedFiles.forEach { file ->
                    if (isImageFile(file)) {
                        paths.add(file.absolutePath)
                    }
                }
            }
        }
        paths.reverse()
        return paths
    }

    fun getFile(context: Context, folderName: String, fileName: String): String {
        val targetDir = File(context.filesDir, folderName)
        return "${targetDir.absolutePath}/$fileName"
    }

//    fun getAllFolder(context: Context): ArrayList<PackageModel>? {
//        val arrayList = ArrayList<PackageModel>()
//        val folders = ArrayList<File>()
//        val packageModel = PackageModel()
//        val packageMap = HashMap<String, ArrayList<File>>()
//
//        val targetDir = File(context.filesDir.absoluteFile, FOLDER_ROOT)
//        if (!targetDir.exists()) targetDir.mkdir()
//
//        if (targetDir.exists() && targetDir.isDirectory) {
//            targetDir.listFiles()?.let { fs ->
//                if (fs.isEmpty()) return null
//                for (folder in fs) {
//                    if (folder.isDirectory) {
//                        folders.add(folder)
//                        folder.listFiles()?.let { f ->
//                            val files = ArrayList<File>()
//                            f.forEach {
//                                if (isImageFile(it)) {
//                                    files.add(it)
//                                }
//                            }
//                            if (packageMap[folder.name] == null) packageMap[folder.name] = files
//                        }
//                    }
//                }
//            }
//        }
//        var folderName: String
//        var files: ArrayList<File>
//        val sortedFolder = folders.sortedBy { it.lastModified() }
//        for (folder in sortedFolder.reversed()) {
//            folderName = folder.name
//            files = packageMap[folderName] ?: arrayListOf()
//            if (files.isEmpty()) {
//                arrayList.add(packageModel.copy(packageName = folderName))
//            } else {
//                val sorterFile = files.sortedBy { it.lastModified() }
//                arrayList.add(packageModel.copy(packageName = folderName, avatarPath = sorterFile.reversed()[0].absolutePath))
//            }
//        }
//        return arrayList
//    }

    fun getAllFiles(context: Context): ArrayList<String> {
        val paths = ArrayList<String>()
        val f = ArrayList<File>()
        val targetDir = context.filesDir

        if (targetDir.exists() && targetDir.isDirectory) {
            targetDir.listFiles()?.let { files ->
                files.forEach { folder ->
                    if (folder.isDirectory) {
                        folder.listFiles()?.let { file ->
                            file.forEach {
                                if (isImageFile(it)) {
                                    f.add(it)
                                }
                            }
                        }
                    }
                }
            }
        }
        val sorterFile = f.sortedBy { it.lastModified() }
        sorterFile.forEach { paths.add(it.absolutePath) }
        return paths
    }

    fun save(context: Context, folderName: String, bitmap: Bitmap): String? {
        val nameFile = "${formatDateTime()}.png"
        val file = File(createFolder(context, folderName), nameFile)
        tryBlock {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        }?.let {
            Log.e("Dunno", "Save Bitmap Exception: ${it.message}")
            return null
        }
        return file.absolutePath
    }

    fun createFolder(context: Context, folderName: String): File {
        val rootFolder = File(context.filesDir.absoluteFile, FOLDER_ROOT)
        if (!rootFolder.exists()) rootFolder.mkdir()

        val folder = File(rootFolder.absoluteFile, folderName)
        if (!folder.exists()) folder.mkdir()
        return folder
    }

    private fun isImageFile(file: File): Boolean {
        val imageExtensions = listOf("png")
        val extension = file.extension.lowercase()
        return file.isFile && imageExtensions.contains(extension)
    }

    fun createFileCache(context: Context, bitmap: Bitmap): String? {
        val nameFile = "${formatDateTime()}.png"
        val file = File(context.cacheDir.absolutePath, nameFile)
        tryBlock {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        }?.let {
            Log.e("Dunno", "Save Bitmap Exception: ${it.message}")
            return null
        }
        return file.absolutePath
    }

    fun delete(path: String): Boolean {
        val file = File(path)
        return if (file.exists()) file.delete() else false
    }

    fun deleteFolder(context: Context, name: String) {
        tryBlock {
            val file = createFolder(context, name)
            if (file.exists() && file.isDirectory) {
                for (f in getFiles(context, name)) {
                    delete(f)
                }
                file.delete()
            }
        }?.let { Log.e("Dunno", it.message.toString()) }
    }

    fun renameFile(context: Context, folderName: String, oldName: String, newName: String, result: (UIState<Boolean>) -> Unit) {
        val oldFile = File(createFolder(context, folderName), "$oldName.png")
        val newFile = File(createFolder(context, folderName), "$newName.png")

        if (oldFile.exists()) {
            val isSuccess = oldFile.renameTo(newFile)
            result.invoke(UIState.Success(isSuccess))
        } else result.invoke(UIState.Failure("file_does_not_exist"))
    }

    fun renameFolder(context: Context, newName: String, oldName: String): Boolean {
        val oldFolder = createFolder(context, oldName)
        val newFolder = createFolder(context, newName)

        return if (oldFolder.exists() && oldFolder.isDirectory) {
            oldFolder.renameTo(newFolder)
        } else false
    }

    fun moveTo(context: Context, folderNameDestination: String, path: String, isDeleteSource: Boolean = false): Boolean {
        val sourceFile = File(path)
        val fileName = path.substringAfterLast("/")

        val folderDestination = createFolder(context, folderNameDestination)
        if (!folderDestination.exists()) return false

        val fileDestination = File(folderDestination, fileName)
        if (fileDestination.exists()) return false

        tryBlock {
            sourceFile.copyTo(fileDestination, overwrite = true)

            if (isDeleteSource) sourceFile.delete()
        }?.let {
            Log.w("Dunno", "Move File Exception: ${it.message}")
            return false
        }
        return true
    }

    fun shareFile(context: Context, path: String) {
        val file = File(path)
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "*/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share File"))
    }

    fun shareFiles(context: Context, paths: ArrayList<String>) {
        val uriList = ArrayList<Uri>()
        var file: File
        var uri: Uri
        for (path in paths) {
            file = File(path)
            uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            uriList.add(uri)
        }
        val intent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            type = "*/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
        }
        val chooser = Intent.createChooser(intent, "Share Images")
        chooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(chooser)
    }

    fun downloadFile(context: Context, path: String): Boolean {
        tryBlock {
            val internalFile = File(path)
            val externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            val externalFile = File(externalDir, "${getMilliseconds()}.png")
            FileInputStream(internalFile).use { input ->
                FileOutputStream(externalFile).use { output ->
                    input.copyTo(output)
                }
            }
            MediaScannerConnection.scanFile(context, arrayOf(externalFile.absolutePath), null) { _, _ -> }
        }?.let {
            Log.e("Dunno", "Download Exception: ${it.message}")
            return false
        }
        return true
    }

    fun downloadFiles(context: Context, paths: ArrayList<String>) {
        for (path in paths) {
            val internalFile = File(path)
            val externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            val externalFile = File(externalDir, "${getMilliseconds()}.png")
            FileInputStream(internalFile).use { input ->
                FileOutputStream(externalFile).use { output ->
                    input.copyTo(output)
                }
            }
            MediaScannerConnection.scanFile(context, arrayOf(externalFile.absolutePath), null) { _, _ -> }
        }
    }

    private const val FOLDER_ROOT = "Image"

}