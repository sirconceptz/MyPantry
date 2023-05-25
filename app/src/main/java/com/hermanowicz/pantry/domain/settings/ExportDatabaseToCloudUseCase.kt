package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ExportDatabaseToCloudUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val storageLocationRepository: StorageLocationRepository
) : suspend () -> Unit {
    override suspend fun invoke() {
        val products = productRepository.observeAll(DatabaseMode.LOCAL).first()
        val categories = categoryRepository.observeAll(DatabaseMode.LOCAL).first()
        val storageLocations = storageLocationRepository.observeAll(DatabaseMode.LOCAL).first()

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
        productRepository.deleteAllLocal()
        categoryRepository.deleteAllLocal()
        storageLocationRepository.deleteAllLocal()
    }
}