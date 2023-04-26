package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import javax.inject.Inject

class SaveProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend (List<Product>) -> List<Long> {
    override suspend fun invoke(products: List<Product>): List<Long> {
        return productRepository.insert(products)
    }
}
