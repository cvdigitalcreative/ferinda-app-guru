package com.digitalcreative.appguru.utils.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.digitalcreative.appguru.utils.preferences.UserPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaHelper @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val preferences: UserPreferences
) {

    @Throws(IOException::class)
    fun createImageFile(): File? {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )

        preferences.setString(UserPreferences.KEY_CERTIFICATE, imageFile.absolutePath)

        return imageFile
    }

    @Suppress("DEPRECATION")
    fun getPath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        return cursor?.let {
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(columnIndex)
        }?.also {
            cursor.close()
        }
    }

    fun compressImage(image: File) {
        BitmapFactory.decodeFile(image.path)?.apply {
            val fos = FileOutputStream(image)
            compress(Bitmap.CompressFormat.JPEG, 50, fos)
            fos.close()
        }
    }

    fun copyImage(uri: Uri, file: File) {
        context.contentResolver.openInputStream(uri)?.let { inputStream ->
            val fileOutputStream = FileOutputStream(file)
            copyStream(inputStream, fileOutputStream)
            fileOutputStream.close()
            inputStream.close()
        }
    }

    fun clearImageInDirectory() {
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.apply {
            list()?.let { children ->
                repeat(children.count()) { i ->
                    File(this, children[i]).delete()
                }
            }
        }
    }

    private fun copyStream(inputStream: InputStream, outputStream: OutputStream) {
        try {
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
