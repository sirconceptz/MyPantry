package com.hermanowicz.pantry.domain.photo

import android.graphics.Bitmap
import com.hermanowicz.pantry.di.repository.PhotoRepository
import javax.inject.Inject

class DecodePhotoFromGalleryUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : (String) -> Bitmap? {
    override fun invoke(fileName: String): Bitmap? {
        return photoRepository.decodePhotoFromGallery(fileName = fileName)
    }
}
