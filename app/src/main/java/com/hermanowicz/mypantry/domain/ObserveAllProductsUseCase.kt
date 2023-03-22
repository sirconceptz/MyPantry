package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.di.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : () -> Flow<List<Product>> {
    override fun invoke(): Flow<List<Product>> {
        return productRepository.observeAll().distinctUntilChanged()
    }
}
