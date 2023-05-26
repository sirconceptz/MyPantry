package com.hermanowicz.pantry.domain.photo

import com.hermanowicz.pantry.di.repository.PhotoRepository
import javax.inject.Inject

class GetPhotoFileNameUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) : () -> String? {
    override fun invoke(): String? {
        return photoRepository.fileName
    }
}
