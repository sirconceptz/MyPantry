package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : (DatabaseMode) -> Flow<List<Product>> {
    override fun invoke(databaseMode: DatabaseMode): Flow<List<Product>> {
        return productRepository.observeAll(databaseMode).distinctUntilChanged()
    }
}
