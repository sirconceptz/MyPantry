package com.hermanowicz.pantry.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import com.hermanowicz.pantry.di.repository.PhotoRepository
import com.hermanowicz.pantry.utils.Constants.PHOTO_DIRECTORY
import com.hermanowicz.pantry.utils.Constants.PHOTO_EXTENSION
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PhotoRepository {

    override var fileName: String? = null

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    override fun createAndGetPhotoFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmm").format(Date())
        val photoFileName = "${PHOTO_DIRECTORY}_${timeStamp}_"
        val storageDir: File? = getPhotoDirectory()
        return File(
            storageDir?.absolutePath + File.separator + photoFileName + PHOTO_EXTENSION
        ).apply {
            fileName = photoFileName
        }
    }

    override fun decodePhotoFromGallery(fileName: String): Bitmap? {
        return try {
            BitmapFactory.decodeFile(getPhotoDirectory()?.absolutePath + File.separator + fileName + PHOTO_EXTENSION)
        } catch (e: Exception) {
            null
        }
    }

    private fun getPhotoDirectory(): File? {
        return context.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES + File.separator + PHOTO_DIRECTORY
        )
    }

    override fun decodeStringToBitmap(string: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    override fun encodeImageToString(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        val encoded = Base64.encodeToString(b, Base64.DEFAULT)
        fileName = encoded
        return encoded
    }
}
