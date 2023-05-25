package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.di.repository.NotificationRepository
import javax.inject.Inject

class DeleteNotificationForProductsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) : suspend (List<Int>) -> Unit {
    override suspend fun invoke(productIdList: List<Int>) {
        notificationRepository.cancelNotification(productIdList)
    }
}