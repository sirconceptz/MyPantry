package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.di.repository.CategoriesRepository
import com.hermanowicz.mypantry.di.repository.ProductRepository
import com.hermanowicz.mypantry.di.repository.StorageLocationRepository
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