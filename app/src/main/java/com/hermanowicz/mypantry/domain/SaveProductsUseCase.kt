package com.hermanowicz.mypantry.domain

import android.content.Context
import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.di.repository.ProductRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SaveProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    @ApplicationContext private val context: Context
) : suspend (List<Product>) -> Unit {
    override suspend fun invoke(products: List<Product>) {
        productRepository.insert(products)
    }
}
