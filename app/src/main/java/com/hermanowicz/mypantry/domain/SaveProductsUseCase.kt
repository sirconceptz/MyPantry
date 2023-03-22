package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.di.repository.ProductRepository
import javax.inject.Inject

class SaveProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend (List<Product>) -> Unit {
    override suspend fun invoke(products: List<Product>) {
        productRepository.insert(products)
    }
}
