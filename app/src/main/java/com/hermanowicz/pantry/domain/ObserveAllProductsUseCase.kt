package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend () -> Flow<List<Product>> {
    override suspend fun invoke(): Flow<List<Product>> {
        return productRepository.observeAll().distinctUntilChanged()
    }
}
