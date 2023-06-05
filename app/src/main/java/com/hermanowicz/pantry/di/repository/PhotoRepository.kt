package com.hermanowicz.pantry.di.repository

import android.graphics.Bitmap
import com.hermanowicz.pantry.data.repository.PhotoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File

interface PhotoRepository {
    fun createAndGetPhotoFile(): File?
    fun decodePhotoFromGallery(fileName: String): Bitmap?
    fun decodeStringToBitmap(string: String): Bitmap?
    fun encodeImageToString(bm: Bitmap): String
    var fileName: String?
}

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoRepositoryModule {

    @Binds
    abstract fun bindPhotoRepository(
        photoRepositoryImpl: PhotoRepositoryImpl
    ): PhotoRepository
}
