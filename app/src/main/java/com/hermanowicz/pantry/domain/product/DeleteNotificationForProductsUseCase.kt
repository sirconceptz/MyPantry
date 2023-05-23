package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.NotificationRepository
import javax.inject.Inject

class DeleteNotificationForProductsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) : suspend (List<Product>) -> Unit {
    override suspend fun invoke(products: List<Product>) {
        notificationRepository.cancelNotification(products.map { it.id })
    }
}