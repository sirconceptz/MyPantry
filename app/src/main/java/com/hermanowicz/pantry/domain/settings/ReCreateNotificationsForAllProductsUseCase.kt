package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.NotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReCreateNotificationsForAllProductsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) : suspend () -> Unit {

    private val job by lazy { SupervisorJob() }
    private val scope by lazy { CoroutineScope(Dispatchers.Main.immediate + job) }

    override suspend fun invoke() {
        scope.launch {
            notificationRepository.cancelNotificationForAllProducts()
            notificationRepository.createNotificationForAllProducts()
        }
    }
}