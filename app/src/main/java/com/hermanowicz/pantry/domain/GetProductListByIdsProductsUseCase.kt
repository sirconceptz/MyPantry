package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductListByIdsProductsUseCase @Inject constructor(
    private val observeAllProductsUseCase: ObserveAllProductsUseCase
) : (DatabaseMode, List<Int>) -> Flow<List<Product>> {
    override fun invoke(databaseMode: DatabaseMode, productIdList: List<Int>): Flow<List<Product>> {
        return observeAllProductsUseCase(databaseMode).map { products ->
            products.filter {
                productIdList.contains(
                    it.id
                )
            }
        }
    }
}
