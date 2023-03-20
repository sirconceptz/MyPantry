package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.di.repository.ProductRepository
import javax.inject.Inject

class DeleteProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend (List<ProductEntity>) -> Unit {
    override suspend fun invoke(products: List<ProductEntity>) {
        productRepository.delete(products)
    }
}
