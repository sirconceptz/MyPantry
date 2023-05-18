package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.di.repository.PhotoRepository
import java.io.File
import javax.inject.Inject

class CreateAndGetPhotoFileUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : () -> File? {
    override fun invoke(): File? {
        return photoRepository.createAndGetPhotoFile()
    }
}