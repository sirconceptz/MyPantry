package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.di.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : () -> Flow<List<ProductEntity>> {
    override fun invoke(): Flow<List<ProductEntity>> {
        return productRepository.observeAll().distinctUntilChanged()
    }

}