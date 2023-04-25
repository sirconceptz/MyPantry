package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import javax.inject.Inject

class ExportDatabaseToCloudUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val storageLocationRepository: StorageLocationRepository
) : suspend () -> Unit {
    override suspend fun invoke() {
        val products = productRepository.getAllLocal()
        val categories = categoryRepository.getAllLocal()
        val storageLocations = storageLocationRepository.getAllLocal()

        cleanDatabases()
        productRepository.insertRemote(products)
        categories.forEach {
            categoryRepository.insertRemote(it)
        }
        storageLocations.forEach {
            storageLocationRepository.insertRemote(it)
        }
    }

    private suspend fun cleanDatabases() {
        productRepository.deleteAllRemote()
        categoryRepository.deleteAllRemote()
        storageLocationRepository.deleteAllRemote()
        productRepository.deleteAllCurrentDatabase()
        categoryRepository.deleteAllCurrentDatabase()
        storageLocationRepository.deleteAllCurrentDatabase()
    }
}