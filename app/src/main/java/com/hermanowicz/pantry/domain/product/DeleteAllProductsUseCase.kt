package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.di.repository.ProductRepository
import javax.inject.Inject

class DeleteAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend () -> Unit {
    override suspend fun invoke() {
        productRepository.deleteAllCurrentDatabase()
    }
}
