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
