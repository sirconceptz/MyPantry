package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.NotificationRepository
import com.hermanowicz.pantry.di.repository.ProductRepository
import javax.inject.Inject

class SaveProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val notificationRepository: NotificationRepository
) : suspend (List<Product>) -> List<Long> {
    override suspend fun invoke(products: List<Product>): List<Long> {
        val listId = productRepository.insert(products)
        val productListWithId = mutableListOf<Product>()
        listId.forEachIndexed { index, id ->
            productListWithId.add(products[index].copy(id = id.toInt()))
        }
        notificationRepository.createNotification(productListWithId.toList())
        return listId
    }
}
