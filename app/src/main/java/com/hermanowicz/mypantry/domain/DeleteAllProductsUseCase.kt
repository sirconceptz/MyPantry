package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.di.repository.ProductRepository
import javax.inject.Inject

class DeleteAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend () -> Unit {
    override suspend fun invoke() {
        productRepository.deleteAll()
    }
}
