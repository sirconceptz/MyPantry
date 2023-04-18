package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.di.repository.CategoriesRepository
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import javax.inject.Inject

class ClearDatabaseToFileUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoriesRepository,
    private val storageLocationRepository: StorageLocationRepository
) : suspend () -> Unit {
    override suspend fun invoke() {
        productRepository.deleteAll()
        categoryRepository.deleteAll()
        storageLocationRepository.deleteAll()
    }
}