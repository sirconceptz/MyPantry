package com.hermanowicz.pantry.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.NotificationRepository
import com.hermanowicz.pantry.domain.FetchDatabaseModeUseCase
import com.hermanowicz.pantry.domain.FetchDaysBeforeNotificationUseCase
import com.hermanowicz.pantry.domain.ObserveAllProductsUseCase
import com.hermanowicz.pantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.pantry.navigation.features.myPantry.state.MyPantryProductsUiState
import com.hermanowicz.pantry.receivers.NotificationBroadcastReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchDaysBeforeNotificationUseCase: FetchDaysBeforeNotificationUseCase,
    private val fetchDatabaseModeUseCase: FetchDatabaseModeUseCase,
    private val observeAllProductsUseCase: ObserveAllProductsUseCase
) : NotificationRepository {
    override suspend fun createNotification(products: List<Product>) {
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        products.forEach { product ->
            intent.putExtra("product_name", product.name)
            intent.putExtra("product_id", product.id)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                product.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (product.expirationDate.isNotEmpty() || product.expirationDate == "-") {
                val calendar: Calendar? = createCalendar(product.expirationDate)
                if (calendar != null)
                    alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
            }
        }
    }

    override fun cancelNotification(products: List<Product>) {
        products.forEach { product ->
            if (product.expirationDate != "-" && product.expirationDate.isNotEmpty()) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, NotificationBroadcastReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    product.id,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
                pendingIntent.cancel()
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    override suspend fun createNotificationForAllProducts() {
        fetchDatabaseModeUseCase().collect { databaseMode ->
            observeAllProductsUseCase(databaseMode).collect { products ->
                createNotification(products)
            }
        }
    }

    override suspend fun cancelNotificationForAllProducts() {
        fetchDatabaseModeUseCase().collect { databaseMode ->
            observeAllProductsUseCase(databaseMode).collect { products ->
                cancelNotification(products)
            }
        }
    }

    private suspend fun createCalendar(expirationDate: String): Calendar? {
        var dateArray = expirationDate.split("\\.")
        if (dateArray.size < 2) {
            dateArray = expirationDate.split("-")
        }
        return if (dateArray.size > 2) {
            val daysBeforeNotification = fetchDaysBeforeNotificationUseCase().first()
            val calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = dateArray[0].toInt()
            calendar[Calendar.MONTH] = dateArray[1].toInt() - 1
            calendar[Calendar.DAY_OF_MONTH] = dateArray[2].toInt()
            calendar[Calendar.HOUR_OF_DAY] = 12
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar.add(Calendar.DAY_OF_YEAR, -daysBeforeNotification)
            calendar
        } else null
    }
}