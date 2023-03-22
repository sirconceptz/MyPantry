package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.di.repository.ProductRepository
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
