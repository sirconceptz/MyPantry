package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : (Int) -> Flow<Product> {
    override fun invoke(id: Int): Flow<Product> {
        return productRepository.observeById(id).distinctUntilChanged()
    }
}
