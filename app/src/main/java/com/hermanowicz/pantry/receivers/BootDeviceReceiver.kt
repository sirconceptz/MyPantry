package com.hermanowicz.pantry.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.domain.settings.ReCreateNotificationsForAllProductsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootDeviceReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reCreateNotificationsForAllProductsUseCase: ReCreateNotificationsForAllProductsUseCase

    @Inject
    lateinit var observeDatabaseModeUseCase: ObserveDatabaseModeUseCase

    private val job by lazy { SupervisorJob() }
    private val scope by lazy { CoroutineScope(Dispatchers.Main.immediate + job) }

    override fun onReceive(context: Context, intent: Intent) {
        scope.launch {
            observeDatabaseModeUseCase().collect { databaseMode ->
                reCreateNotificationsForAllProductsUseCase(
                    oldDatabaseMode = databaseMode,
                    newDatabaseMode = databaseMode
                )
            }
        }
    }
}
