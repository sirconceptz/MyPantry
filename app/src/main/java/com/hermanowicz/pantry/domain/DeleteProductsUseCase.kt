package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import javax.inject.Inject

class DeleteProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend (List<Int>) -> Unit {
    override suspend fun invoke(productIds: List<Int>) {
        productRepository.delete(productIds)
    }
}
