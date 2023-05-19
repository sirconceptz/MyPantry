package com.hermanowicz.pantry.domain

import android.graphics.Bitmap
import com.hermanowicz.pantry.di.repository.PhotoRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import javax.inject.Inject

class FetchPhotoBitmapUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : (String, DatabaseMode) -> Bitmap? {
    override fun invoke(fileName: String, databaseMode: DatabaseMode): Bitmap? {
        return if (databaseMode == DatabaseMode.LOCAL)
            photoRepository.decodePhotoFromGallery(fileName = fileName)
        else {
            photoRepository.decodeStringToBitmap(fileName)
        }
    }
}