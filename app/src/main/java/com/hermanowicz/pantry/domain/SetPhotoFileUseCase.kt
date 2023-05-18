package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.di.repository.PhotoRepository
import javax.inject.Inject

class SetPhotoFileUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : (String) -> Unit {
    override fun invoke(photoFileName: String) {
        photoRepository.fileName = photoFileName
    }
}