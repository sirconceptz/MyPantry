package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveProductByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : (Int, DatabaseMode) -> Flow<Product> {
    override fun invoke(id: Int, databaseMode: DatabaseMode): Flow<Product> {
        return productRepository.observeById(id, databaseMode).distinctUntilChanged()
    }
}
