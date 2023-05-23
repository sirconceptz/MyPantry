package com.hermanowicz.pantry.domain.settings

import com.hermanowicz.pantry.di.repository.NotificationRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReCreateNotificationsForAllProductsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) : suspend (DatabaseMode, DatabaseMode) -> Unit {

    private val job by lazy { SupervisorJob() }
    private val scope by lazy { CoroutineScope(Dispatchers.Main.immediate + job) }

    override suspend fun invoke(oldDatabaseMode: DatabaseMode, newDatabaseMode: DatabaseMode) {
        scope.launch {
            notificationRepository.cancelNotificationForAllProducts(oldDatabaseMode)
            notificationRepository.createNotificationForAllProducts(newDatabaseMode)
        }
    }
}