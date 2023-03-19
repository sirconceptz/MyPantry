package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.di.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : (Int) -> Flow<ProductEntity> {
    override fun invoke(id: Int): Flow<ProductEntity> {
        return productRepository.observeById(id).distinctUntilChanged()
    }
}